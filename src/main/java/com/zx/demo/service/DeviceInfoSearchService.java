package com.zx.demo.service;

import com.huawei.iotplatform.client.NorthApiException;
import com.huawei.iotplatform.client.dto.QueryDataHistoryOutDTO;
import com.zx.demo.domain.Device;
import com.zx.demo.domain.DeviceInfo;
import com.zx.demo.model.DeviceInfo_all;
import com.zx.demo.model.DeviceInfo_detail;

import java.util.Date;
import java.util.List;

/**
 * 向远程服务器查询信息
 * */
public interface DeviceInfoSearchService {
    DeviceInfo getDeviceInfoFromRemoteServer(String deviceId);

    List<String> getAllDeviceIdListFromRemoteServer();

    List<Device> getAllDeviceFromRemoteServer();

    void refreshDevicesInfoFromRemoteServer() throws NorthApiException;

    void refreshDevicesInfoFromRemoteServer_() throws NorthApiException;

    String getIEMENumberByDeviceId(String deviceId) throws NorthApiException;

    String getDeviceNameFromRemoteServer(String deviceId) throws NorthApiException;

    DeviceInfo_all replaceDeviceInfoFromRemoteServer(DeviceInfo_all deviceInfo_all) throws NorthApiException;

    DeviceInfo_detail replaceDeviceInfoFromRemoteServer(DeviceInfo_detail deviceInfo_detail) throws NorthApiException;

    QueryDataHistoryOutDTO getDeviceInfo_allByDeviceIdFromRemoteServer(int pageSize, int pageNo, String deviceId, Date date_startTime, Date date_endTime) throws NorthApiException;

    QueryDataHistoryOutDTO getDeviceInfo_allByDeviceIdFromRemoteServer(int pageSize, int pageNo, String deviceId) throws NorthApiException;

    void Test() throws NorthApiException;
}
