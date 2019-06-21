package com.zx.demo.service.implement;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.huawei.iotplatform.client.NorthApiException;
import com.huawei.iotplatform.client.dto.*;
import com.huawei.iotplatform.client.invokeapi.DataCollection;
import com.zx.demo.domain.Device;
import com.zx.demo.domain.DeviceInfo;
import com.zx.demo.domain.NewDeviceInfo;
import com.zx.demo.model.DeviceInfo_all;
import com.zx.demo.model.DeviceInfo_detail;
import com.zx.demo.model.DeviceInfo_saveToDataBase;
import com.zx.demo.repository.DeviceInfoDao;
import com.zx.demo.repository.NewDeviceInfoDao;
import com.zx.demo.service.DeviceInfoSearchService;
import com.zx.demo.service.DeviceInfoService;
import com.zx.demo.service.DeviceService;
import com.zx.demo.util.DeviceRemoteSearchUtil;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnJava;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service("deviceInfoService")
public class DeviceInfoServiceImp implements DeviceInfoService {
    @Autowired
    private DeviceInfoDao deviceInfoDao;

    @Autowired
    private NewDeviceInfoDao newDeviceInfoDao;

    @Autowired
    private DeviceService deviceService;

    @Override
    public Map<String, Object> getDeviceInfo_all_normal(int pageNumber, int limit, String property, String order, long userId, String search){
        Map<String, Object> map = new HashMap<>();

        List<DeviceInfo_all> deviceInfoList = deviceInfoDao.getLatestDeviceInfo_all_normal(userId, search);

        List<DeviceInfo_all> deviceInfo_result = new LinkedList<>();

        deviceInfoList = sort(deviceInfoList,property,order);

        int pageNum = 0;
        for(int i = pageNumber*limit; i < deviceInfoList.size(); i++){
            if(pageNum >= limit){
                break;
            }

            if(deviceInfoList.get(i).getDeviceId()==16){
                System.out.println("");
            }

            deviceInfo_result.add(deviceInfoList.get(i));

            pageNum++;
        }

        map.put("total", deviceInfoList.size());
        map.put("rows", deviceInfo_result);

        return map;
    }

    @Override
    public Map<String, Object> getDeviceInfo_all_abnormal(int pageNumber, int limit, String property, String order, long userId, String search){
        Map<String, Object> map = new HashMap<>();

        List<DeviceInfo_all> deviceInfoList = deviceInfoDao.getLatestDeviceInfo_all_abnormal(userId, search);

        List<DeviceInfo_all> deviceInfo_result = new LinkedList<>();

        deviceInfoList = sort(deviceInfoList,property,order);

        int pageNum = 0;
        for(int i = pageNumber*limit; i < deviceInfoList.size(); i++){
            if(pageNum >= limit){
                break;
            }

            deviceInfo_result.add(deviceInfoList.get(i));

            pageNum++;
        }

        map.put("total", deviceInfoList.size());
        map.put("rows", deviceInfo_result);

        return map;
    }


    private List<DeviceInfo_all> sort(List<DeviceInfo_all> list, String property, String order){
        int len=list.size();

        double value = 0;

        for(int i=0;i<len;i++){//循环次数
            switch (property){
                case "temperature" :
                    value = list.get(i).getTemperature();

                    break;
                case "humidity" :
                    value = list.get(i).getHumidity();
                    break;
            }

            int position=i;

            for(int j=i+1;j<len;j++){//找到最小的值和位置
                double value_=0;

                switch (property){
                    case "temperature" :
                        value_ = list.get(j).getTemperature();

                        break;
                    case "humidity" :
                        value_ = list.get(j).getHumidity();
                        break;
                }

                if(order.equals("desc")){
                    if(value_ >= value){
                        value = value_;
                        position=j;
                    }
                } else {
                    if(value_ <= value){
                        value = value_;
                        position=j;
                    }
                }
            }

            DeviceInfo_all temp = list.get(position);

            list.set(position, list.get(i));
            list.set(i,temp);
        }

        return list;
    }

    @Override
    public DeviceInfo_detail getDeviceInfo_detail(Pageable pageable, long deviceId){
        Page<DeviceInfo_detail> page = deviceInfoDao.getDeviceInfo_detail(pageable, deviceId);

        if(page.getTotalPages() > 0){
            return page.getContent().get(0);
        }

        return new DeviceInfo_detail();
    }

    @Override
    public Map<String, Object> getTemperatureRecord_Local(long id, String dateTime, int select_mode){
        Map<String, Object> map = new HashMap<>();

        List<DeviceInfo> deviceInfoList = deviceInfoDao.getTemperatureRecord_local(id,dateTime);

        List<Double> temperatureRecord = new LinkedList<>();
        List<String> timeRecord = new LinkedList<>();


        if(select_mode == 0){//0点，24条数据，一个小时一个数据
            for(int i = 0; i < deviceInfoList.size(); i+=2){
                temperatureRecord.add(deviceInfoList.get(i).getTemperature());
                timeRecord.add(String.valueOf(deviceInfoList.get(i).getModify_time_search_local()));
            }

        } else {//24条数据，半个小时一个数据
            for(int i = 0; i < 24 && i < deviceInfoList.size(); i++){
                temperatureRecord.add(deviceInfoList.get(i).getTemperature());
                timeRecord.add(String.valueOf(deviceInfoList.get(i).getModify_time_search_local()));
            }
        }

        map.put("temperatureRecord",temperatureRecord);
        map.put("timeRecord",timeRecord);

        return map;
    }

    @Override
    public Map<String, Object> getHumidityRecord_Local(long id, String dateTime, int select_mode){
        Map<String, Object> map = new HashMap<>();

        List<DeviceInfo> deviceInfoList = deviceInfoDao.getHumidityRecord_local(id,dateTime);

        List<Double> humidityRecord = new LinkedList<>();
        List<String> timeRecord = new LinkedList<>();


        if(select_mode == 0){//0点，24条数据，一个小时一个数据
            for(int i = 0; i < deviceInfoList.size(); i+=2){
                humidityRecord.add(deviceInfoList.get(i).getHumidity());
                timeRecord.add(String.valueOf(deviceInfoList.get(i).getModify_time_search_local()));
            }

        } else {//24条数据，半个小时一个数据
            for(int i = 0; i < 24 && i < deviceInfoList.size(); i++){
                humidityRecord.add(deviceInfoList.get(i).getHumidity());
                timeRecord.add(String.valueOf(deviceInfoList.get(i).getModify_time_search_local()));
            }
        }

        map.put("humidityRecord",humidityRecord);
        map.put("timeRecord",timeRecord);

        return map;
    }


    @Override
    public void addDeviceInfo(DeviceInfo deviceInfo) {
        deviceInfoDao.save(deviceInfo);
    }

    @Override
    public void removeAllNewNewDeviceInfo(){
        newDeviceInfoDao.deleteAll();
    }

    @Override
    public void addNewDeviceInfo(NewDeviceInfo newDeviceInfo) {
        newDeviceInfoDao.save(newDeviceInfo);
    }

}
