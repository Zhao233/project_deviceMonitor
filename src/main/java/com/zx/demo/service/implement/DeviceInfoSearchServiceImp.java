package com.zx.demo.service.implement;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.huawei.iotplatform.client.NorthApiException;
import com.huawei.iotplatform.client.dto.*;
import com.huawei.iotplatform.client.invokeapi.DataCollection;
import com.zx.demo.domain.Device;
import com.zx.demo.domain.DeviceInfo;
import com.zx.demo.domain.User;
import com.zx.demo.model.DeviceInfo_all;
import com.zx.demo.model.DeviceInfo_detail;
import com.zx.demo.model.DeviceInfo_saveToDataBase;
import com.zx.demo.service.DeviceInfoSearchService;
import com.zx.demo.service.DeviceInfoService;
import com.zx.demo.service.DeviceService;
import com.zx.demo.service.UserService;
import com.zx.demo.util.DeviceRemoteSearchUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

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

    @Override
    public DeviceInfo getDeviceInfoFromRemoteServer(String deviceId) {
        DataCollection collection = new DataCollection(DeviceRemoteSearchUtil.northApiClient);

        try {
            QueryDeviceDataOutDTO deviceDataOutDTO = collection.queryDeviceData(deviceId, DeviceRemoteSearchUtil.appId, DeviceRemoteSearchUtil.authOutDTO.getAccessToken());

            System.out.println( deviceDataOutDTO.getServices().size() );

            int length = deviceDataOutDTO.getServices().size();

            if( length == 1 ){
                return null;
            }

            ObjectNode objectNode = null;

            String signalIntensity = "";

            if( length == 10){
                objectNode = deviceDataOutDTO.getServices().get(4).getData();

                signalIntensity = deviceDataOutDTO.getServices().get(3).getData().get("signalStrength").asText();
            } else {
                objectNode = deviceDataOutDTO.getServices().get(5).getData();

                signalIntensity = deviceDataOutDTO.getServices().get(4).getData().get("signalStrength").asText();
            }

            String device_mac_id = deviceDataOutDTO.getDeviceId();
            int device_id = (int) deviceService.getIdByDevice_mac_id(device_mac_id);

            double humidity = Double.parseDouble(String.format("%.1f",objectNode.get("humidity").asDouble()));
            double temperature = Double.parseDouble(String.format("%.1f", objectNode.get("temperature").asDouble()));
            int light = objectNode.get("light").asInt();
            int battery = objectNode.get("battery").asInt();

            String status = deviceDataOutDTO.getDeviceInfo().getStatus();
            String imei_id = deviceDataOutDTO.getDeviceInfo().getNodeId();

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd'T'hhmmss'Z'");
            Date date_update = simpleDateFormat.parse(deviceDataOutDTO.getLastModifiedTime());
            Calendar calendar_update = Calendar.getInstance();
            calendar_update.setTime(date_update);
            calendar_update.set(Calendar.HOUR_OF_DAY, calendar_update.get(Calendar.HOUR_OF_DAY) + 8);
            Timestamp timestamp_update = new Timestamp(calendar_update.getTimeInMillis());

            Date date_now = new Date();
            Calendar calendar_now = Calendar.getInstance();
            calendar_now.setTime(date_now);
            calendar_now.set(Calendar.HOUR_OF_DAY, calendar_now.get(Calendar.HOUR_OF_DAY));
            Timestamp timestamp_now = new Timestamp(calendar_now.getTimeInMillis());

            DeviceInfo deviceInfo = new DeviceInfo(0,device_mac_id,device_id,humidity,status,signalIntensity,battery,light,temperature,0,imei_id,timestamp_update,timestamp_now);

            System.out.println(deviceInfo.toString());

            return deviceInfo;
        } catch (NorthApiException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 根据此appid, secret 下，远程服务器所拥有的所有设备的device_mac_id
     * */
    @Override
    public List<String> getAllDeviceIdListFromRemoteServer() {
        DataCollection collection = new DataCollection(DeviceRemoteSearchUtil.northApiClient);

        try {
            QueryDevicesInDTO queryDevicesInDTO = new QueryDevicesInDTO();

            QueryDevicesOutDTO devicesOutDTO = collection.queryDevices(queryDevicesInDTO, DeviceRemoteSearchUtil.appId, DeviceRemoteSearchUtil.authOutDTO.getAccessToken());

            List<QueryDeviceDTO> devicesList = devicesOutDTO.getDevices();
            List<String> devicesIdList = new LinkedList<>();

            for (QueryDeviceDTO deviceDTO : devicesList) {
                devicesIdList.add(deviceDTO.getDeviceId());
            }
        } catch (NorthApiException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 根据此appid, secret 下，远程服务器所拥有的所有设备
     * */
    @Override
    public List<Device> getAllDeviceFromRemoteServer() {
        DataCollection collection = new DataCollection(DeviceRemoteSearchUtil.northApiClient);

        try {
            QueryDevicesInDTO queryDevicesInDTO = new QueryDevicesInDTO();
            queryDevicesInDTO.setPageSize(Integer.MAX_VALUE);
            queryDevicesInDTO.setPageNo(0);

            QueryDevicesOutDTO devicesOutDTO = collection.queryDevices(queryDevicesInDTO, DeviceRemoteSearchUtil.appId, DeviceRemoteSearchUtil.authOutDTO.getAccessToken());

            List<QueryDeviceDTO> devicesList = devicesOutDTO.getDevices();

            List<Device> devicesIdList = new LinkedList<>();

            Timestamp timestamp = new Timestamp(new Date().getTime());

            for (QueryDeviceDTO queryDeviceDTO : devicesList) {
                com.huawei.iotplatform.client.dto.DeviceInfo deviceInfo = queryDeviceDTO.getDeviceInfo();

                String deviceName = deviceInfo.getName();
                int type = 0;
                String addressOfDevice = deviceInfo.getLocation();
                String attributionOfDevice = "";
                String organization_name = "";
                int organizationId = 0;
                int level = 0;
                String mac_id = queryDeviceDTO.getDeviceId();

                Device device = new Device(0, deviceName, type, addressOfDevice, attributionOfDevice, organization_name, organizationId, level, mac_id, timestamp, timestamp, deviceName,0);

                devicesIdList.add(device);
            }

            return devicesIdList;

        } catch (NorthApiException e) {
            e.printStackTrace();
        }

        return new LinkedList<Device>();
    }

    /**
     * 根据本地的设备device_mac_id 去从远程服务器更新数据
     * */
//    @Override
//    public void refreshDevicesInfoFromRemoteServer() throws NorthApiException {
//        Pageable pageable = new PageRequest(0, Integer.MAX_VALUE, new Sort(Sort.Direction.DESC, "id"));
//
//        List<Device> deviceList_temp = deviceService.getAllDevice(pageable).getContent();
//
//        for(Device device_temp : deviceList_temp){
//            DeviceInfo deviceInfo = getDeviceInfoFromRemoteServer( device_temp.getMac_id() );
//
//            if(deviceInfo != null){
//                //deviceInfoService.addDeviceInfo(deviceInfo);
//            }
//        }
//    }

    @Override
    public void refreshDevicesInfoFromRemoteServer_() throws NorthApiException {
        List<User> userList = userService.findAll();

        for(User userTemp : userList){
            DeviceRemoteSearchUtil.appId = userTemp.getAppId();
            DeviceRemoteSearchUtil.secret = userTemp.getSecret_service();
            //重新获取accessToken
            DeviceRemoteSearchUtil.initRemoteServer();

            List<Device> deviceList = deviceService.getDevicesByUserId(userTemp.getId());

            for(Device deviceTemp : deviceList){
                DeviceInfo deviceInfo = getDeviceInfoFromRemoteServer( deviceTemp.getMac_id() );

                if(deviceInfo != null){
                    deviceInfoService.addDeviceInfo(deviceInfo);
                    System.out.println(deviceInfo.toString());
                }
            }
        }
    }

    @Override
    public String getIEMENumberByDeviceId(String deviceId) throws NorthApiException {
        DataCollection collection = new DataCollection(DeviceRemoteSearchUtil.northApiClient);

        QueryDeviceDataOutDTO queryDevicesOutDTO = collection.queryDeviceData(deviceId, DeviceRemoteSearchUtil.appId, DeviceRemoteSearchUtil.authOutDTO.getAccessToken());

        System.out.println(queryDevicesOutDTO.getDeviceInfo().getName());

        return queryDevicesOutDTO.getDeviceInfo().getNodeId();
    }

    @Override
    public String getDeviceNameFromRemoteServer(String deviceId) throws NorthApiException {
        DataCollection collection = new DataCollection(DeviceRemoteSearchUtil.northApiClient);

        QueryDeviceDataOutDTO queryDevicesOutDTO = collection.queryDeviceData(deviceId, DeviceRemoteSearchUtil.appId, DeviceRemoteSearchUtil.authOutDTO.getAccessToken());

        return queryDevicesOutDTO.getDeviceInfo().getName();
    }

    @Override
    public DeviceInfo_all replaceDeviceInfoFromRemoteServer(DeviceInfo_all deviceInfo_all) throws NorthApiException {
//        DataCollection collection = new DataCollection(DeviceRemoteSearchUtil.northApiClient);
//        QueryDeviceDataOutDTO queryDevicesOutDTO = collection.queryDeviceData(deviceInfo_all.getDeviceId(), DeviceRemoteSearchUtil.appId, DeviceRemoteSearchUtil.authOutDTO.getAccessToken());
//
//        deviceInfo_all.setName(queryDevicesOutDTO.getDeviceInfo().getName());
//        deviceInfo_all.setDeviceId(queryDevicesOutDTO.getDeviceInfo().getNodeId());

        return null;
    }

    @Override
    public DeviceInfo_detail replaceDeviceInfoFromRemoteServer(DeviceInfo_detail deviceInfo_detail) throws NorthApiException {
//        DataCollection collection = new DataCollection(DeviceRemoteSearchUtil.northApiClient);
//        QueryDeviceDataOutDTO queryDevicesOutDTO = collection.queryDeviceData(deviceInfo_detail.getDeviceId(), DeviceRemoteSearchUtil.appId, DeviceRemoteSearchUtil.authOutDTO.getAccessToken());
//
//        deviceInfo_detail.setName(queryDevicesOutDTO.getDeviceInfo().getName());
//        deviceInfo_detail.setDeviceId(queryDevicesOutDTO.getDeviceInfo().getNodeId());
//
//        deviceInfo_detail.setAddressOfDevice(queryDevicesOutDTO.getDeviceInfo().getLocation());

        return null;
    }

    /**
     * 新接口，所有数据从远程服务器获取
     * */
    @Override
    public QueryDataHistoryOutDTO getDeviceInfo_allByDeviceIdFromRemoteServer(int pageSize, int pageNo, String deviceId, Date date_startTime, Date date_endTime) throws NorthApiException {
        DataCollection dataCollection = new DataCollection();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYYMMdd'T'hhmmss");

        QueryDataHistoryInDTO dataHistoryInDTO = new QueryDataHistoryInDTO();
        dataHistoryInDTO.setPageNo(pageNo);
        dataHistoryInDTO.setPageSize(pageSize);
        dataHistoryInDTO.setDeviceId(deviceId);
        dataHistoryInDTO.setGatewayId(deviceId);
        dataHistoryInDTO.setStartTime(simpleDateFormat.format(date_startTime));
        dataHistoryInDTO.setEndTime(simpleDateFormat.format(date_endTime));

        return dataCollection.queryDataHistory(dataHistoryInDTO,DeviceRemoteSearchUtil.appId,DeviceRemoteSearchUtil.secret);
    }

    @Override
    public QueryDataHistoryOutDTO getDeviceInfo_allByDeviceIdFromRemoteServer(int pageSize, int pageNo, String deviceId) throws NorthApiException {
        DataCollection dataCollection = new DataCollection();

        QueryDataHistoryInDTO dataHistoryInDTO = new QueryDataHistoryInDTO();
        dataHistoryInDTO.setPageNo(pageNo);
        dataHistoryInDTO.setPageSize(pageSize);
        dataHistoryInDTO.setDeviceId(deviceId);
        dataHistoryInDTO.setGatewayId(deviceId);

        return dataCollection.queryDataHistory(dataHistoryInDTO,DeviceRemoteSearchUtil.appId,DeviceRemoteSearchUtil.secret);
    }

}
