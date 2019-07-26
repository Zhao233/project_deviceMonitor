package com.zx.demo.service.Admin;

import com.zx.demo.domain.UserLevel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface UserLevelService {
    /**管理员*/

    //查看所有用户
    Page<UserLevel> getAll(String search, Pageable pageable);

    //新增用户信息
    void addOneForAdmin(String description, Integer level);

    //根据id获得一个用户
    UserLevel getOneByIdForAdmin(Long id);

    //修改用户
    void updateOneForAdmin(UserLevel userLevel);

    //删除用户
    void deleteOneForAdmin(Long id);
}
