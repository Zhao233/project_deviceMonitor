package com.zx.demo.controller;
import com.zx.demo.configuration.CustomMvcConfigurer;
import com.zx.demo.domain.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class IndexController {
    @RequestMapping(value = "/RTSP")
    public ModelAndView toRTSP(){
        return new ModelAndView("/RTSP");
    }

    @RequestMapping(value = {"/", "/login"})
    public ModelAndView toHome(HttpServletRequest request, HttpServletResponse response) {
        User user = (User)request.getSession().getAttribute("user");


        if (user != null) {
            switch ( user.getRole() ){
                case CustomMvcConfigurer.ROLE_CUSTOMER :
                    return new ModelAndView("redirect:user/device_info");

                case CustomMvcConfigurer.ROLE_ADMIN:
                    return new ModelAndView("redirect:console/device_management");
            }


        }

        return new ModelAndView("login");
    }

    @RequestMapping("/logged")
    public String logged(HttpServletRequest request, HttpServletResponse response) throws Exception {

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (user != null) {
            user.setPassword(null);
            request.getSession().setAttribute("user", user);
        }

        return "redirect:/login";

//        if(user.getRole().equals("PM"))
//        {
//            return "redirect:/console/employee_management";
//        }else if(user.getRole().equals("S")){
//            return "redirect:/console/employee_management";
//        }else if(user.getRole().equals("C")){
//            return "redirect:/console/employee_management";
//        }else if(user.getRole().equals("A")){
//            return "redirect:/console/employee_management";
//        }else {
//            return "redirect:/console";
//        }

    }

    @RequestMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if( request.getSession().getAttribute("user") != null){
            request.getSession().setAttribute("user",null);
        }

        response.sendRedirect("/login");
    }

}