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

    User findByName(String name);

    User findById(long id);

    @Query("select user from User user where user.name like %?1% "
            + " or user.level like %?1%")
    Page<User> findAll(String q, Pageable pageable);

    void deleteById(long id);
}
