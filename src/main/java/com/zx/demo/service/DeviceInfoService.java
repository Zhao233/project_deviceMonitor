package com.zx.demo.service;

import com.huawei.iotplatform.client.NorthApiException;
import com.zx.demo.domain.DeviceInfo;
import com.zx.demo.domain.NewDeviceInfo;
import com.zx.demo.model.DeviceInfo_all;
import com.zx.demo.model.DeviceInfo_detail;
import com.zx.demo.model.DeviceInfo_saveToDataBase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

public interface DeviceInfoService {

    Map<String, Object> getDeviceInfo_all_normal(int pageNumber, int limit, String property, String order, long userId, String search);

    Map<String, Object> getDeviceInfo_all_abnormal(int pageNumber, int limit, String property, String order, long userId, String search);


    DeviceInfo_detail getDeviceInfo_detail(Pageable pageable, long deviceId);

    Map<String, Object> getTemperatureRecord_Local(long id, String dateTime, int select_mode);

    Map<String, Object> getHumidityRecord_Local(long id, String dateTime, int select_mode);


    void addDeviceInfo(DeviceInfo deviceInfo);

    void removeAllNewNewDeviceInfo();

    void addNewDeviceInfo(NewDeviceInfo newDeviceInfo);
}
