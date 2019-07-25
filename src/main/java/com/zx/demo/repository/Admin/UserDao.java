package com.zx.demo.repository.Admin;

import com.zx.demo.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends JpaRepository <User,Long> {
    /**管理员*/

    //用于在登录时找出对应用户
    @Query(value = "SELECT user FROM User user WHERE user.name=?1")
    User findByName(String name);

    //查看所有用户
    @Query(value = "SELECT user FROM User user WHERE user.name = ?1")
    Page<User> getAll(String search, Pageable pageable);
}
