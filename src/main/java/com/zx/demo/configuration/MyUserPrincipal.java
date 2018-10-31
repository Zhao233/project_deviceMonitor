package com.zx.demo.configuration;

import com.zx.demo.domain.User;
import org.springframework.security.core.userdetails.UserDetails;

public abstract class MyUserPrincipal implements UserDetails {
    private User user;

    public MyUserPrincipal(User user) {
        this.user = user;
    }
    //...
}