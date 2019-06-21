package com.zx.demo.service.implement;

import com.fasterxml.jackson.databind.JsonNode;
import com.huawei.iotplatform.client.NorthApiException;
import com.huawei.iotplatform.client.dto.*;
import com.huawei.iotplatform.client.invokeapi.DataCollection;
import com.zx.demo.domain.Device;
import com.zx.demo.domain.DeviceInfo;
import com.zx.demo.domain.User;
import com.zx.demo.model.DeviceInfo_all;
import com.zx.demo.model.DeviceInfo_detail;
import com.zx.demo.service.DeviceInfoSearchService;
import com.zx.demo.service.DeviceInfoService;
import com.zx.demo.service.DeviceService;
import com.zx.demo.service.UserService;
import com.zx.demo.util.DeviceRemoteSearchUtil;
import com.zx.demo.util_api.Constant;
import com.zx.demo.util_api.HttpsUtil;
import com.zx.demo.util_api.JsonUtil;
import com.zx.demo.util_api.StreamClosedHttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 向远程服务器查询信息
 * */

@Service("deviceInfoSearchService")
public class DeviceInfoSearchServiceImp implements DeviceInfoSearchService {
//    @Autowired
//    DeviceInfoService deviceInfoService;

    @Autowired
    DeviceService deviceService;

    @Autowired
    UserService userService;

    @Autowired
    DeviceInfoService deviceInfoService;

    /**
     * 从服务器获取信息
     * */
    @Override
    public DeviceInfo[] getDeviceInfoFromRemoteServer(String device_mac_Id, String appid, String secrete) throws Exception {
        DeviceInfo[] infos = new DeviceInfo[2];

        JsonNode node = DeviceRemoteSearchUtil.getResult(device_mac_Id);


        JsonNode response = node.get("error_code");

        if(response != null){
            System.out.println("error desc : " + node.get("error_desc").toString() + "device Id : "+ device_mac_Id);

            return null;
        }

        JsonNode temp = node.get("services");

        if(temp == null){
            return null;
        }




        String signalIntensity = "";

        double humidity = 0;
        double temperature = 0;
        int light = 0;
        int battery = 0;

        String status = "";
        String imei_id = "";

        DeviceInfo deviceInfo = null;

        for (int i = 0; i < temp.size(); i++) {
            JsonNode temp_node = temp.get(i);

            //获取signalIntensity
            if( temp_node.get("serviceId").toString().equals("\"SignalStrength\"") ){
                signalIntensity = temp_node.get("data").get("signalStrength").toString();

                signalIntensity = signalIntensity.substring(1, signalIntensity.length() - 1);
            }

            if ( temp_node.get("serviceId").toString().equals("\"DataMinute30\"") ) {//半点的记录
                JsonNode temp_data = temp_node.get("data");

                String temp_battery = temp_data.get("battery").toString();
                String temp_light = temp_data.get("light").toString();
                String temp_temperature = temp_data.get("temperature").toString();
                String temp_humidity = temp_data.get("humidity").toString();

                temp_battery = temp_battery.substring(1, temp_battery.length() - 1);
                temp_light = temp_light.substring(1, temp_light.length() - 1);
                temp_temperature = temp_temperature.substring(1, temp_temperature.length() - 1);
                temp_humidity = temp_humidity.substring(1, temp_humidity.length() - 1);

                battery = Integer.parseInt(temp_battery);
                light = temp_light.equals("off") ? 1 : 0 ;
                temperature = Double.parseDouble(temp_temperature);
                humidity = Double.parseDouble(temp_humidity);

                status = node.get("deviceInfo").get("status").toString();
                imei_id = node.get("deviceInfo").get("name").toString();
                imei_id = imei_id.substring(1, imei_id.length() - 1);

                String lastModifiedTime = node.get("lastModifiedTime").toString();
                lastModifiedTime = lastModifiedTime.substring(1, lastModifiedTime.length() - 1);

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd'T'hhmmss'Z'");
                Date date_update = simpleDateFormat.parse(lastModifiedTime);
                Calendar calendar_update = Calendar.getInstance();
                calendar_update.setTime(date_update);
                calendar_update.set(Calendar.HOUR_OF_DAY, calendar_update.get(Calendar.HOUR_OF_DAY) + 8);
                calendar_update.set(Calendar.MINUTE, 30);
                Timestamp timestamp_update = new Timestamp(calendar_update.getTimeInMillis());

                Date date_now = new Date();
                Calendar calendar_now = Calendar.getInstance();
                calendar_now.setTime(date_now);
                calendar_now.set(Calendar.HOUR_OF_DAY, calendar_now.get(Calendar.HOUR_OF_DAY));
                Timestamp timestamp_now = new Timestamp(calendar_now.getTimeInMillis());

                long deviceId = deviceService.getIdByDevice_mac_id(device_mac_Id);

                deviceInfo = new DeviceInfo(0,device_mac_Id, deviceId, humidity, status, signalIntensity, battery, light, temperature, 0, imei_id, timestamp_update, timestamp_now );

                infos[0] = deviceInfo;
            }

            if ( temp_node.get("serviceId").toString().equals("\"DataMinute60\"") ) {//整点的记录
                JsonNode temp_data = temp_node.get("data");

                String temp_battery = temp_data.get("battery").toString();
                String temp_light = temp_data.get("light").toString();
                String temp_temperature = temp_data.get("temperature").toString();
                String temp_humidity = temp_data.get("humidity").toString();

                temp_battery = temp_battery.substring(1, temp_battery.length() - 1);
                temp_light = temp_light.substring(1, temp_light.length() - 1);
                temp_temperature = temp_temperature.substring(1, temp_temperature.length() - 1);
                temp_humidity = temp_humidity.substring(1, temp_humidity.length() - 1);

                battery = Integer.parseInt(temp_battery);
                light = temp_light.equals("off") ? 1 : 0 ;
                temperature = Double.parseDouble(temp_temperature);
                humidity = Double.parseDouble(temp_humidity);

                status = node.get("deviceInfo").get("status").toString();
                imei_id = node.get("deviceInfo").get("name").toString();
                imei_id = imei_id.substring(1, imei_id.length() - 1);

                String lastModifiedTime = node.get("lastModifiedTime").toString();
                lastModifiedTime = lastModifiedTime.substring(1, lastModifiedTime.length() - 1);

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd'T'hhmmss'Z'");
                Date date_update = simpleDateFormat.parse(lastModifiedTime);
                Calendar calendar_update = Calendar.getInstance();
                calendar_update.setTime(date_update);
                calendar_update.set(Calendar.HOUR_OF_DAY, calendar_update.get(Calendar.HOUR_OF_DAY) + 8);
                calendar_update.set(Calendar.MINUTE, 59);
                Timestamp timestamp_update = new Timestamp(calendar_update.getTimeInMillis());

                Date date_now = new Date();
                Calendar calendar_now = Calendar.getInstance();
                calendar_now.setTime(date_now);
                calendar_now.set(Calendar.HOUR_OF_DAY, calendar_now.get(Calendar.HOUR_OF_DAY));
                Timestamp timestamp_now = new Timestamp(calendar_now.getTimeInMillis());

                long deviceId = deviceService.getIdByDevice_mac_id(device_mac_Id);

                deviceInfo = new DeviceInfo(0,device_mac_Id, deviceId, humidity, status, signalIntensity, battery, light, temperature, 0, imei_id, timestamp_update, timestamp_now );

                infos[1] = deviceInfo;
            }
        }

        return infos;
    }

    @Override
    public void refreshDevicesInfoFromRemoteServer() throws Exception {
        deviceInfoService.removeAllNewNewDeviceInfo();//清除记录最新设备信息的表中的信息

        List<User> userList = userService.findAll();

        for(User userTemp : userList){
            if(userTemp.getSecret_service() == null || userTemp.getSecret_service().equals("")){
                //如果是管理员就跳过
                continue;
            }

            DeviceRemoteSearchUtil.appId = userTemp.getAppId();
            DeviceRemoteSearchUtil.secret = userTemp.getSecret_service();

            HttpsUtil httpsUtil = new HttpsUtil();
            httpsUtil.initSSLConfigForTwoWay();

            DeviceRemoteSearchUtil.login(httpsUtil);

            List<Device> deviceList = deviceService.getDevicesByUserId(userTemp.getId());

            for(Device deviceTemp : deviceList){
                DeviceInfo[] deviceInfo = getDeviceInfoFromRemoteServer( deviceTemp.getMac_id(), userTemp.getAppId(), userTemp.getSecret_service() );

                if(deviceInfo != null){
                    deviceInfoService.addDeviceInfo(deviceInfo[0]);
                    deviceInfoService.addDeviceInfo(deviceInfo[1]);

                    deviceInfoService.addNewDeviceInfo(deviceInfo[0].toNewDeviceInfo());
                    deviceInfoService.addNewDeviceInfo(deviceInfo[1].toNewDeviceInfo());

                    System.out.println(deviceInfo.toString());
                }
            }
        }
    }
}
