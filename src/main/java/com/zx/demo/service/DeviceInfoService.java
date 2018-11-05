package com.zx.demo.service;

import com.huawei.iotplatform.client.NorthApiException;
import com.zx.demo.domain.DeviceInfo;
import com.zx.demo.model.DeviceInfo_detail;
import com.zx.demo.model.DeviceInfo_saveToDataBase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

public interface DeviceInfoService {
    Map<String, Object> getAll_latest(int offset, int pageSize) throws NorthApiException, ParseException;


    Page<DeviceInfo> getAll_latest(Pageable pageable, long userId);

    Map<String, Object> getAllDeviceInfoFromRemoteService(int pageNo, int pageSize) throws NorthApiException, ParseException;

    DeviceInfo getDeviceInfoById(long id);

    DeviceInfo_detail getDeviceInfoDetailById(long id) throws NorthApiException, ParseException;

    String getOrganizationName(long organizationId);

    Map<String, Object> getTemperatureRecord(long id, String dateTime, int mode) throws Exception;

    Map<String, Object> getHumidityRecord(long id, String dateTime, int time_gap) throws Exception;

    Map<String, Object> getTemperatureRecord_Local(long id, String dateTime, int time_gap);

    Map<String, Object> getHumidityRecord_Local(long id, String dateTime, int select_mode);

    int getSearchTimes(String device_mac_id);

    void addDeviceInfo(DeviceInfo deviceInfo);

    List<DeviceInfo_saveToDataBase> getAllDeviceMacIdAndDeviceId();
}
