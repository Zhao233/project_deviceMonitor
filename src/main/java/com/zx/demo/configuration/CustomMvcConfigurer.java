package com.zx.demo.configuration;

import com.zx.demo.domain.User;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Configuration
public class CustomMvcConfigurer extends WebMvcConfigurerAdapter {
    public static final int ROLE_CUSTOMER = 0;
    public static final int ROLE_ADMIN = 1;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        super.addInterceptors(registry);

        registry.addInterceptor(new SecurityInterceptor()).addPathPatterns("/user/*").excludePathPatterns("/login");
        registry.addInterceptor(new SecurityInterceptor_ConsoleCheck()).addPathPatterns("/console/*").excludePathPatterns("/login");
    }

    /**
     * 拦截器
     * */
    static class SecurityInterceptor implements HandlerInterceptor {
        @Override
        public boolean preHandle(HttpServletRequest request,
                                 HttpServletResponse response,
                                 Object handler) throws Exception {

            User user = (User) request.getSession().getAttribute("user");

            if (user != null) {


            } else {
                response.sendRedirect("/login");

                return false;
            }

            return true;
        }

        @Override
        public void postHandle(HttpServletRequest request,
                               HttpServletResponse response,
                               Object handler,
                               ModelAndView modelAndView) throws Exception {

        }

        @Override
        public void afterCompletion(HttpServletRequest request,
                                    HttpServletResponse response,
                                    Object handler, Exception ex) throws Exception {
        }
    }

    static class SecurityInterceptor_ConsoleCheck implements HandlerInterceptor {
        @Override
        public boolean preHandle(HttpServletRequest request,
                                 HttpServletResponse response,
                                 Object handler) throws Exception {
            User user = (User) request.getSession().getAttribute("user");

            if (user != null) {

                String uri = request.getRequestURI();

                if(user.getRole() == CustomMvcConfigurer.ROLE_CUSTOMER && uri.contains("/console")){
                    //不是管理员却访问与管理员界面相关的页面

                    response.sendRedirect("/login?error=");

                    return false;
                }
            } else {
                response.sendRedirect("/login");

                return false;
            }

            return true;
        }

        @Override
        public void postHandle(HttpServletRequest request,
                               HttpServletResponse response,
                               Object handler,
                               ModelAndView modelAndView) throws Exception {

        }

        @Override
        public void afterCompletion(HttpServletRequest request,
                                    HttpServletResponse response,
                                    Object handler, Exception ex) throws Exception {
        }
    }

}
