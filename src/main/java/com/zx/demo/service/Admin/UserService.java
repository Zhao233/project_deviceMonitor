package com.zx.demo.service.Admin;

import com.zx.demo.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface UserService{
        /**管理员*/
        List<User> findAll();

        //查看所有用户
        Page<User> getAll(String search, Pageable pageable);

        //新增用户信息
        void addOneForAdmin(User user);

        //根据id获得一个用户
        User getOneByIdForAdmin(Long id);

        //根据name获取一个用户
        User getOneByNameForAdmin(String name);

        //修改用户
        void updateOneForAdmin(User user);

        //删除用户
        void deleteOneForAdmin(Long id);
}