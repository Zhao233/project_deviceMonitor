package com.zx.demo.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Collection;

@Entity
@Table(name = "user")

@Getter
@Setter
public class User implements UserDetails {
    public final static int LEVEL_1 = 1;
    public final static int LEVEL_2 = 2;
    public final static int LEVEL_3 = 3;
    public final static int LEVEL_4 = 4;
    public final static int LEVEL_5 = 5;

    @Id
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "password")
    private String password;

    @Column(name = "level")
    private int level;


    @Column(name = "appId")
    private String appId;


    @Column(name = "secret_service")
    private String secret_service;

    public User converter() {
        User user = new User();

//        user.setId(this.id);
////        user.setName( this.name.trim().toLowerCase());
////        user.setPassword(this.password.trim());
////        user.setLevel(this.level);

        return user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return name;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }


    @Override
    public boolean isEnabled() {
        return true;
    }


}
