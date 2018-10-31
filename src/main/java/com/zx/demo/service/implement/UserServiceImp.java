package com.zx.demo.service.implement;

import com.zx.demo.domain.User;
import com.zx.demo.repository.UserDao;
import com.zx.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("userService")
@Transactional
public abstract class UserServiceImp implements UserService {

}
