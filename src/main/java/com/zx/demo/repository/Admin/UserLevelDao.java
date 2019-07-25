package com.zx.demo.repository.Admin;

import com.zx.demo.domain.User;
import com.zx.demo.domain.UserLevel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserLevelDao extends JpaRepository<UserLevel,Long> {

    /**管理员*/

    //查看所有用户
    @Query(value = "SELECT userLevel FROM UserLevel userLevel WHERE userLevel.description = ?1")
    Page<User> getAll(String search, Pageable pageable);
}
