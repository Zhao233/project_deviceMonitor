package com.zx.demo.controller;

import com.zx.demo.domain.Device;
import com.zx.demo.domain.User;
import com.zx.demo.service.DeviceService;
import com.zx.demo.util.UserUtil;
import org.apache.catalina.connector.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/console/device_management")
public class DeviceController {
    @Autowired
    private DeviceService deviceService;

    @RequestMapping("/getAll")
    @ResponseBody
    private Map<String,Object> getAllDevice(@RequestParam("search") String search,
                                            @RequestParam("limit") Integer limit,
                                            @RequestParam("offset") Integer offset){
        Map<String,Object> map = new HashMap<>();

        User user = UserUtil.getCureentUser();

        Pageable pageable = new PageRequest(offset, limit, new Sort(Sort.Direction.DESC, "id"));

        Page<Device> page;

        if ("".equals(search.trim())) {
            page = deviceService.getAllDevice(pageable, user.getId());
        } else {
            page = deviceService.getAllDevice(search.trim(), pageable, user.getId());
        }

        map.put("total", page != null ? page.getTotalElements() : 0);
        map.put("rows", page != null ? page.getContent() : "");

        return map;
    }

    @RequestMapping("/get/{id}")
    @ResponseBody
    private Device getDeviceById(@PathVariable int id){

        Device device = deviceService.getDeviceById(id);

        return device;
    }

    @RequestMapping("/add")
    @ResponseBody
    private Map<String, Object> addDevice(@Valid Device device){
        Map<String, Object> map = new HashMap<>();

        //如果数据库中已经存在含有设备ID的设备，就不添加
        if( deviceService.isExist( device.getMac_id() ) ){
            map.put("status","FAILED");
            map.put("message","设备deviceId已存在");

        } else {
            Timestamp timestamp = new Timestamp(new Date().getTime());

            device.setCreate_time(timestamp);
            device.setUpdate_time(timestamp);

            deviceService.addDevice(device);
            map.put("status", "SUCCEED");
        }

        return map;
    }

    @RequestMapping("/update")
    @ResponseBody
    private Map<String, Object> updateDevice(@RequestParam("id") long id,        @RequestParam("mac_id") String deviceId,
                                             @RequestParam("name") String name, @RequestParam("attributionOfDevice") String attributionOfDevice,
                                             @RequestParam("organization_name") String organization_name, @RequestParam("addressOfDevice") String addressOfDevice,
                                             @RequestParam("type") int type,    @RequestParam("level") int level,
                                             @RequestParam("createTime") String createTime, @RequestParam("updateTime") String updateTime,
                                             @RequestParam("deviceId_old") String deviceId_old){
        Map<String, Object> map = new HashMap<>();

        //如果数据库中已经存在含有设备ID的设备，就不添加
        if( deviceService.isExist_compare_newAndOld(deviceId, deviceId_old) ){
            map.put("status","FAILED");
            map.put("message","设备deviceId已存在");

        } else {
            Date date = new Date();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {

                date = simpleDateFormat.parse(createTime);

            } catch (ParseException e) {
                e.printStackTrace();
            }


            Timestamp timestamp = new Timestamp(new Date().getTime());

            Timestamp timestamp_create = new Timestamp(new Date().getTime());
            timestamp_create.setTime(date.getTime());

//            Device device = new Device(id, name, type, addressOfDevice, attributionOfDevice, organization_name, 0,level,deviceId,timestamp,timestamp_create,name,0);
//
//            deviceService.updateDevice(device);
//            map.put("status", "SUCCEED");

        }

        return map;
    }

    @RequestMapping("/delete")
    @ResponseBody
    private Map<String, Object> deleteDevice(@RequestParam("list_ID") List<Long> id ){
        Map<String, Object> map = new HashMap<>();

        for(Long temp_id : id){
            deviceService.deleteDeviceById(temp_id);
        }

        map.put("status","SUCCEED");

        return map;
    }
}
