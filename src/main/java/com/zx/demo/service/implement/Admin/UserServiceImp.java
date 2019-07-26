package com.zx.demo.service.implement;

import com.zx.demo.domain.User;
import com.zx.demo.repository.Admin.UserDao;
import com.zx.demo.service.Admin.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userService")
public class UserServiceImp implements UserService{
    @Autowired
    private UserDao userDao;

    /**管理员*/

    @Override
    public List<User> findAll() {
        return userDao.findAll();
    }

    @Override
    public Page<User> getAll(String search, Pageable pageable) {
        return userDao.getAll(search, pageable);
    }

    @Override
    public void addOneForAdmin(User user) {
        userDao.save(user);
    }

    @Override
    public User getOneByIdForAdmin(Long id) {
        return userDao.getOne(id);
    }

    @Override
    public User getOneByNameForAdmin(String name) {
        return userDao.findByName(name);
    }

    @Override
    public void updateOneForAdmin(User user) {
        userDao.save(user);
    }

    @Override
    public void deleteOneForAdmin(Long id) {
        userDao.deleteById(id);
    }
}
