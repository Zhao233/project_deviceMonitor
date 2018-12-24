package com.zx.demo.controller;

import com.zx.demo.domain.User;
import com.zx.demo.repository.UserDao;
import org.omg.CORBA.OBJ_ADAPTER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/console/user_management")
public class UserManagementController {
    @Autowired
    private UserDao userDao;

    @RequestMapping("/search")
    @ResponseBody
    public Map<String, Object> search(@RequestParam(value = "search", defaultValue = "") String search,
                                      @RequestParam(value = "offset", defaultValue = "0") Integer offset,
                                      @RequestParam(value = "limit", defaultValue = "10") Integer limit) {
        Map<String, Object> p = new HashMap<>();

        Pageable pageable = new PageRequest(offset, limit, new Sort(Sort.Direction.DESC, "id"));
        Page<User> page;

        if ("".equals(search.trim())) {
            page = userDao.findAll(pageable);
        } else {
            //page = userDao.findAll(search.trim(), pageable);
        }

        //测试 可删除
        page = userDao.findAll(pageable);


        p.put("total", page != null ? page.getTotalElements() : 0);
        p.put("rows", page != null ? page.getContent() : "");

        return p;
    }


}
