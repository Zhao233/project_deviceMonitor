package com.zx.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TemplateController {
    @RequestMapping("/device_info")
    private ModelAndView toDeviceInfo(){
        System.out.print("device_info");
        return new ModelAndView("device_info");
    }

    @RequestMapping("/device_info_detail")
    private ModelAndView toDeviceInfoDetail(){
        return new ModelAndView("device_info_detail");
    }

    @RequestMapping("/temperature_detail")
    private ModelAndView toTemperatureDetail(){
        return new ModelAndView("temperature_detail");
    }

    @RequestMapping("/humidity_detail")
    private ModelAndView toHumidityDetail(){
        return new ModelAndView("humidity_detail");
    }

}
