package com.zx.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/console")
public class ConsoleController {


    @RequestMapping("/device_info")
    private ModelAndView toDeviceInfo(){
        System.out.print("device_info");
        return new ModelAndView("console/device_info");
    }

    @RequestMapping("/device_info_detail")
    private ModelAndView toDeviceInfoDetail(){
         return new ModelAndView("console/device_info_detail");
    }

    @RequestMapping("/temperature_detail")
    private ModelAndView toTemperatureDetail(){
        return new ModelAndView("console/temperature_detail");
    }

    @RequestMapping("/humidity_detail")
    private ModelAndView toHumidityDetail(){
        return new ModelAndView("console/humidity_detail");
    }

    @RequestMapping("/device_management")
    private ModelAndView toDeviceManager(){
        return new ModelAndView("console/device_management");
    }

    @RequestMapping("/test")
    private ModelAndView toTest(){
        return new ModelAndView("/test");
    }
}
