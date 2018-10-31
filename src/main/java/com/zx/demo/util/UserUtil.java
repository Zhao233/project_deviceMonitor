package com.zx.demo.util;

import com.zx.demo.domain.User;
import org.apache.http.HttpRequest;

import javax.servlet.http.HttpServletRequest;

public class UserUtil {
    public static User getCureentUser(HttpServletRequest request){
        return (User) request.getSession().getAttribute("user");
    }
}
