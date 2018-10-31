package com.zx.demo.controller;

import com.zx.demo.domain.Admin;
import com.zx.demo.repository.AdminDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ControllerTest {

    @RequestMapping("/hello")
    @ResponseBody
    private String run(){
        Admin admin = new Admin();
        admin.setId(2);
        admin.setPassword("asdf");
        admin.setName("asdfasdf");


        return "hello";
    }
}
