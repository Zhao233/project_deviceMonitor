package com.zx.demo.controller;

import com.zx.demo.domain.Config;
import com.zx.demo.service.ConfigService;
import com.zx.demo.service.DeviceInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.huawei.iotplatform.client.NorthApiException;
import com.zx.demo.domain.DeviceInfo;
import com.zx.demo.domain.User;
import com.zx.demo.model.DeviceInfo_all;
import com.zx.demo.model.DeviceInfo_detail;
import com.zx.demo.service.DeviceInfoService;
import com.zx.demo.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**与设备信息查询有关的服务*/
@Controller
@RequestMapping("/console/deviceInfo")
public class DeviceServiceController {

    @Autowired
    private DeviceInfoService deviceInfoService;

    @Autowired
    private ConfigService configService;

    @RequestMapping("/searchAll")
    @ResponseBody
    public Map<String, Object> search(@RequestParam(value = "offset", defaultValue = "0") Integer offset,
                                      @RequestParam(value = "limit", defaultValue = "10") Integer limit,
                                      @RequestParam(value = "order", required = false) String order,//升序或者降序
                                      @RequestParam(value = "sort" , required = false) String sort,//排序名

                                      @RequestParam(value = "isNormal", required = false) boolean isNormal,//排序名

                                      @RequestParam(value = "search") String search,

                                      @RequestParam(value = "limit", defaultValue = "10", required = false) Integer type,
                                      @RequestParam(value = "limit", defaultValue = "10", required = false) Integer level,
                                      @RequestParam(value = "limit", defaultValue = "10", required = false) Integer statusOfLight,
                                      @RequestParam(value = "limit", defaultValue = "10", required = false) Integer statusOfCharge,
                                      @RequestParam(value = "limit", defaultValue = "10", required = false) Integer temperatur,
                                      @RequestParam(value = "limit", defaultValue = "10", required = false) Integer humidity,
                                      @RequestParam(value = "limit", defaultValue = "10", required = false) Integer updateTime
                                      ) {
        Map<String, Object> p = new HashMap<>();
        Map<String, Object> res;

        Pageable pageable;

        if(order != null && sort != null && !sort.equals("stargazers_count")){
            Sort.Direction sort_ = order.equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;

            pageable = new PageRequest(offset, limit, sort_, sort);
        } else {//默认

            pageable = new PageRequest(offset, limit, new Sort(Sort.Direction.DESC, "id"));
        }

        User user = UserUtil.getCureentUser();

        if( !isNormal  ){//查找不正常设备（电信平台更新时间与本地时间不同）
            res = deviceInfoService.getDeviceInfo_all_abnormal(offset, limit, sort, order,user.getId(), search);
        } else {//查找正常设备
            res = deviceInfoService.getDeviceInfo_all_normal(offset, limit, sort, order, user.getId(), search);
        }

        List<DeviceInfo_all> list = (List<DeviceInfo_all>) res.get("rows");
        int total = (int) res.get("total");

        p.put("total", total);
        p.put("rows", list);

        return p;
    }

    @RequestMapping("/getDeviceInfo_detail")
    @ResponseBody
    private Map<String, Object> getDeviceInfo_DetailById(@RequestParam(value = "deviceId") int deviceId ){
        Map<String, Object> map = new HashMap<>();
        Pageable pageable = new PageRequest(0, Integer.MAX_VALUE, new Sort(Sort.Direction.DESC, "id"));

        DeviceInfo_detail deviceInfo_detail = deviceInfoService.getDeviceInfo_detail(pageable, deviceId);

        map.put("deviceInfo", deviceInfo_detail);
        map.put("status", "SUCCEED");

        return map;
    }

    /**
     * select_mode:
     *      0: 按照24小时, 每一小时一个数据
     *      1: 按照12小时, 每半个小时一个数据, 00:00 点开始
     *      2: 按照12小时, 每半个小时一个数据, 12:00 点开始
     * */
    @RequestMapping("/getTemperatureRecord")
    @ResponseBody
    private Map<String, Object> getTemperatureRecord(@RequestParam(value = "deviceId") int deviceId,
                                                     @RequestParam(value = "dateTime") String dateTime,
                                                     @RequestParam(value = "select_mode") int select_mode ) throws ParseException, NorthApiException {
        Map<String, Object> map = new HashMap<>();

        Map<String,Object> res = deviceInfoService.getTemperatureRecord_Local(deviceId,dateTime,select_mode);

        List<String> temperatureRecord = (List<String>) res.get("temperatureRecord");
        List<String> timeRecord = (List<String>) res.get("timeRecord");

        Config config = configService.getConfig(0);
        String temperatureUpperLimit = config.getTemperature_upper_limit();
        String temperatureLowerLimit = config.getTemperature_lower_limit();
//
//        Map<String,Object> res = new HashMap<>();
//
//        List<String> temperatureRecord = null;
//        List<String> updateTimes = null;
//
//        Date date = null;
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyyMMdd'T'HHmmss'Z'");
//
//        date = simpleDateFormat.parse(dateTime);
//
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(date);
//
//        calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY)-8);
//
//        String dateTime_formmated = simpleDateFormat1.format(calendar.getTime());
//
//        try {
//            res = deviceInfoService.getTemperatureRecord_Local(deviceId,dateTime_formmated,select_mode);
//
//            temperatureRecord = (LinkedList)res.get("temperatureRecord");
//
//            updateTimes = (LinkedList)res.get("timeRecord");
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        map.put("temperatureRecord", temperatureRecord);
        map.put("updateTimeRecord", timeRecord);

        map.put("temperatureUpperLimit", temperatureUpperLimit);
        map.put("temperatureLowerLimit", temperatureLowerLimit);

        map.put("status", "SUCCEED");

        return map;
    }


    /**
     * select_mode:
     *      0: 按照24小时, 每一小时一个数据
     *      1: 按照12小时, 每半个小时一个数据, 00:00 点开始
     *      2: 按照12小时, 每半个小时一个数据, 12:00 点开始
     * */
    @RequestMapping("/getHumidityRecord")
    @ResponseBody
    private Map<String, Object> getHumidityRecord(@RequestParam(value = "deviceId") int deviceId,
                                                     @RequestParam(value = "dateTime") String dateTime,
                                                     @RequestParam(value = "select_mode") int select_mode) throws ParseException {
        Map<String, Object> map = new HashMap<>();

        Map<String,Object> res = deviceInfoService.getHumidityRecord_Local(deviceId,dateTime,select_mode);

        List<String> humidityRecord = (List<String>) res.get("humidityRecord");
        List<String> timeRecord = (List<String>) res.get("timeRecord");

        Config config = configService.getConfig(0);
        String humidityUpperLimit = config.getHumidity_upper_limit();
        String humidityLowerLimit = config.getHumidity_lower_limit();

        map.put("humidityRecord", humidityRecord);
        map.put("updateTimeRecord", timeRecord);

        map.put("humidityUpperLimit", humidityUpperLimit);
        map.put("humidityLowerLimit", humidityLowerLimit);

        map.put("status", "SUCCEED");

        return map;
    }

    @RequestMapping("/test")
    @ResponseBody
    private String test(){
        return "success";
    }

}