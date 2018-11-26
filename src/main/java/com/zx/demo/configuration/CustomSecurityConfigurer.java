package com.zx.demo.configuration;

import com.zx.demo.domain.User;
import com.zx.demo.repository.UserDao;
import com.zx.demo.util.PasswordEncodeAssistant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Configuration
@EnableWebSecurity
public class CustomSecurityConfigurer extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        ( auth.userDetailsService(userDetailsService) )
                .passwordEncoder(
                        new PasswordEncoder() {
                            @Override
                            public String encode(CharSequence charSequence) {
                                return PasswordEncodeAssistant.encode(((String) charSequence).toCharArray());
                            }

                            @Override
                            public boolean matches(CharSequence charSequence, String encodedPassword) {
                                return encodedPassword.equals(encode(charSequence));
                            }
                        }
                );
    }

    /**
     *页面验证
     * */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/", "/style/**", "/javascript/**", "/fonts/**", "/api/**", "/console/**", "/images/**", "/voices/**", "/image/**", "/", "/RTSP/*").permitAll() //
                .anyRequest().authenticated()
                .and().formLogin().loginPage("/login").successForwardUrl("/logged").failureUrl("/?error").permitAll() //
                .and().logout().logoutUrl("/logout").logoutSuccessUrl("/").permitAll(); //

        http.csrf().disable();

        super.configure(http);
    }

    @Service("userDetailsService")
    static class CustomUserDetailsService implements UserDetailsService {

        /**
         * 登陆功能
         *
         * */

        @Autowired
        private UserDao userDao;

        @Override
        public UserDetails loadUserByUsername(String username) {

            User user = userDao.findByName(username);

            if (user == null) {
                throw new UsernameNotFoundException(username);
            } else {
            }

            return user;
        }
    }
}