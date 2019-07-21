package com.zx.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/console")
public class ConsoleController {
    @RequestMapping("/device_management")
    private ModelAndView toDeviceManager(){
        return new ModelAndView("console/device_management");
    }

    @RequestMapping("/config")
    private ModelAndView toConfig(){
        return new ModelAndView("console/config");
    }

    @RequestMapping("/user_management")
    private ModelAndView toUserManagement(){
        return new ModelAndView("console/user_management");
    }

    @RequestMapping("/getMenu")
    private ModelAndView getAdminMenu(){
        return new ModelAndView("console/menu");
    }


    @RequestMapping("/test")
    private ModelAndView toTest(){
        return new ModelAndView("/test");
    }
}
