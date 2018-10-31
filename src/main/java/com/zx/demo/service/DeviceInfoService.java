package com.zx.demo.service;

import com.huawei.iotplatform.client.NorthApiException;
import com.zx.demo.domain.DeviceInfo;
import com.zx.demo.model.DeviceInfo_detail;
import com.zx.demo.model.DeviceInfo_saveToDataBase;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

public interface DeviceInfoService {
    Map<String, Object> getAll_latest(int offset, int pageSize) throws NorthApiException, ParseException;

    Map<String, Object> getAllDeviceInfoFromRemoteService(int pageNo, int pageSize) throws NorthApiException, ParseException;

    DeviceInfo getDeviceInfoById(long id);

    DeviceInfo_detail getDeviceInfoDetailById(long id) throws NorthApiException, ParseException;

    String getOrganizationName(long organizationId);

    Map<String, Object> getTemperatureRecord(long id, String dateTime, int mode) throws Exception;

    Map<String, Object> getHumidityRecord(long id, String dateTime, int time_gap) throws Exception;

    int getSearchTimes(String device_mac_id);

    void addDeviceInfo(DeviceInfo deviceInfo);

    List<DeviceInfo_saveToDataBase> getAllDeviceMacIdAndDeviceId();
}
