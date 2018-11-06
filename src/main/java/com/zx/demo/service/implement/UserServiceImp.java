package com.zx.demo.service.implement;

import com.zx.demo.domain.User;
import com.zx.demo.repository.UserDao;
import com.zx.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userService")
public class UserServiceImp implements UserService{
    @Autowired
    private UserDao userDao;


    @Override
    public List<User> findAll() {
        return userDao.findAll();
    }
}
