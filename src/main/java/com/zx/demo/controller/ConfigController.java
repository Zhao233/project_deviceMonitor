package com.zx.demo.controller;

import com.zx.demo.domain.Config;
import com.zx.demo.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/console/config_management")
public class ConfigController {
    @Autowired
    private ConfigService configService;

    @PostMapping("/save")
    @ResponseBody
    private Map<String,Object> updateConfig(@Param("temperatureUpperLimit") String temperatureUpperLimit,
                                            @Param("temperatureLowerLimit") String temperatureLowerLimit,
                                            @Param("humidityUpperLimit") String humidityUpperLimit,
                                            @Param("humidityLowerLimit") String humidityLowerLimit){
        Map<String,Object> map = new HashMap<>();

        Config config = configService.getConfig(0);
        config.setHumidity_upper_limit(humidityUpperLimit);
        config.setHumidity_lower_limit(humidityLowerLimit);
        config.setTemperature_upper_limit(temperatureUpperLimit);
        config.setTemperature_lower_limit(temperatureLowerLimit);

        configService.save(config);

        map.put("status", "SUCCEED");

        return map;
    }

    @RequestMapping("/getConfig")
    @ResponseBody
    private Map<String,Object> getConfig(){
        Map<String,Object> map = new HashMap<>();

        Config config = configService.getConfig(0);

        map.put("temperatureUpperLimit", config.getTemperature_upper_limit());
        map.put("temperatureLowerLimit", config.getTemperature_lower_limit());
        map.put("humidityUpperLimit", config.getHumidity_upper_limit());
        map.put("humidityLowerLimit", config.getHumidity_lower_limit());

        map.put("status","SUCCEED");

        return map;
    }
}
