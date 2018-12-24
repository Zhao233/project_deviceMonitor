package com.zx.demo.repository;

import com.zx.demo.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends JpaRepository <User,Long> {
    @Query(value = "select user from User user where user.name=?1")
    User findByName(String name);
}
