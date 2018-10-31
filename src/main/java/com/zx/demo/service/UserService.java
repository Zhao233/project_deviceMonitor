package com.zx.demo.service;

import com.zx.demo.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface UserService  extends JpaRepository<User, Long> {
        UserDetails findByUsername(String username);

        User findByEmail(String email);

        User findById(long id);

//        @Query("select user from User user where user.username like %?1% "
//        + " or user.firstName like %?1% or user.lastName like %?1% or user.email like %?1%")
//        Page<User> findAll(String q, Pageable pageable);

        List<User> findByRoleAndEnabled(String role, boolean enabled);

        void deleteById(long id);

    UserDetails findByName(String name);
}