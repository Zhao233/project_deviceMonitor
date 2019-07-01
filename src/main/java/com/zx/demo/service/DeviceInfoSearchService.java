package com.zx.demo.service;

import com.fasterxml.jackson.databind.JsonNode;
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
    DeviceInfo[] getDeviceInfoFromRemoteServer(String device_mac_Id, String appid, String secrete) throws Exception;

    DeviceInfo[] getDeviceInfoFromRemoteServer(JsonNode node) throws Exception;

    void refreshDevicesInfoFromRemoteServer() throws Exception;

    void refreshDevicesInfoFromRemoteServer_() throws Exception;
}
