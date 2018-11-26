package com.zx.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TemplateController {
    @RequestMapping("/user/device_info")
    private ModelAndView toDeviceInfo(){
        System.out.print("device_info");
        return new ModelAndView("user/device_info");
    }

    @RequestMapping("/user/device_info_detail")
    private ModelAndView toDeviceInfoDetail(){
        return new ModelAndView("user/device_info_detail");
    }

    @RequestMapping("/user/temperature_detail")
    private ModelAndView toTemperatureDetail(){
        return new ModelAndView("user/temperature_detail");
    }

    @RequestMapping("/user/humidity_detail")
    private ModelAndView toHumidityDetail(){
        return new ModelAndView("user/humidity_detail");
    }

}
