package com.zx.demo.service.implement;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.huawei.iotplatform.client.NorthApiException;
import com.huawei.iotplatform.client.dto.*;
import com.huawei.iotplatform.client.invokeapi.DataCollection;
import com.zx.demo.domain.Device;
import com.zx.demo.domain.DeviceInfo;
import com.zx.demo.model.DeviceInfo_all;
import com.zx.demo.model.DeviceInfo_detail;
import com.zx.demo.model.DeviceInfo_saveToDataBase;
import com.zx.demo.repository.DeviceInfoDao;
import com.zx.demo.service.DeviceInfoSearchService;
import com.zx.demo.service.DeviceInfoService;
import com.zx.demo.service.DeviceService;
import com.zx.demo.util.DeviceRemoteSearchUtil;
import org.springframework.beans.factory.annotation.Autowired;
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
    private DeviceService deviceService;

    @Override
    public Page<DeviceInfo_all> getDeviceInfo_all(Pageable pageable, long userId){
        return deviceInfoDao.getLatestDeviceInfo_all(pageable, userId);
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

        List<String> temperatureRecord = new LinkedList<>();
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

        List<String> humidityRecord = new LinkedList<>();
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
    public int getSearchTimes(String device_mac_id) {
        return 0;
    }

    @Override
    public void addDeviceInfo(DeviceInfo deviceInfo) {
        deviceInfoDao.save(deviceInfo);
    }


    /**
     * 进行远程搜索
     * */
//    @Override
//    public Map<String, Object> getAll_latest(int pageNo, int pageSize, long userId) throws NorthApiException, ParseException {
//        Map<String, Object> res = new HashMap<>();
//
//        Map<String ,Object> map = getAllDeviceInfoFromRemoteService(pageNo, pageSize);
//
//        List<DeviceInfo> deviceInfoList = (LinkedList)map.get("deviceInfoList");
//
//        long total = (long)map.get("total");
//
//        List<DeviceInfo_all> deviceInfo_allList = new LinkedList<>();
//
//        for(DeviceInfo deviceInfo : deviceInfoList){
//            String device_mac_id = deviceInfo.getDevice_mac_id();
//            long device_id = deviceService.getIdByDevice_mac_id(device_mac_id);
//
//            int type = 0;
//            int level = 0;
//
//            String name = deviceService.getDeviceNameById(device_id);
//            int light = deviceInfo.getStatusOfLight();
//            int charge = deviceInfo.getStatusOfCharge();
//            String temperature = deviceInfo.getTemperature();
//            String humidity = deviceInfo.getHumidity();
//            Date updateTime = deviceInfo.getUpdateTime();
//            String imei_id = deviceInfo.getImei_id();
//
//            DeviceInfo_all deviceInfo_all = new DeviceInfo_all(device_id,device_mac_id,type,level,name, light,charge,temperature,humidity,updateTime,imei_id);
//
//            deviceInfo_allList.add(deviceInfo_all);
//        }
//
//        res.put("deviceInfo_allList", deviceInfo_allList);
//        res.put("total", total);
//
//        return res;
//    }
//
//    @Override
//    public Page<DeviceInfo> getAll_latest_(Pageable pageable, long userId){
//        return deviceInfoDao.getLatestDeviceInfo_all(userId,pageable);
//    }

//    /**
//     * 进行远程搜索
//     * */
//    @Override
//    public Map<String, Object> getAllDeviceInfoFromRemoteService(int pageNo, int pageSize) throws NorthApiException, ParseException {
//        Map<String, Object> res = new HashMap<>();
//
//        DataCollection dataCollection = new DataCollection(DeviceRemoteSearchUtil.northApiClient);
//
//        QueryDevicesInDTO queryDevicesInDTO = new QueryDevicesInDTO();
//        queryDevicesInDTO.setPageNo(pageNo);
//        queryDevicesInDTO.setPageSize(pageSize);
//
//        QueryDevicesOutDTO queryDevicesOutDTO =  dataCollection.queryDevices(queryDevicesInDTO, DeviceRemoteSearchUtil.appId, DeviceRemoteSearchUtil.authOutDTO.getAccessToken());
//
//        List<DeviceInfo> deviceInfoList = new LinkedList<>();
//
//        for( QueryDeviceDTO queryDeviceDTO : queryDevicesOutDTO.getDevices() ){
//            //网关设备
//            if(queryDeviceDTO.getServices().size() < 10 ){
//                System.out.println("adsffffffffffffffffff");
//
//                continue;
//            }
//
//            ObjectNode node = queryDeviceDTO.getServices().get(9).getData();
//
//
//            String eventTime = queryDeviceDTO.getServices().get(9).getEventTime();
//            eventTime = eventTime.replace("T","");
//            eventTime = eventTime.replace("Z","");
//
//            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddhhmmss");
//
//
//            String device_mac_id =  queryDeviceDTO.getDeviceId();
//            int device_id = 0;//待定
//            String humidity = String.format("%.1f", node.get("humidity").asDouble());
//            String status = queryDeviceDTO.getDeviceInfo().getStatus();
//
//            String signalIntensity = "";
//
//            if(queryDeviceDTO.getServices().size() == 10){
//                signalIntensity = queryDeviceDTO.getServices().get(3).getData().get("signalStrength").asText();
//            } else {
//                signalIntensity = queryDeviceDTO.getServices().get(4).getData().get("signalStrength").asText();
//            }
//
//            int statusOfCharge = node.get("battery").asInt();
//            int statusOfLight = node.get("light").asInt();
//            String temperature = String.format("%.1f", node.get("temperature").asDouble());
//            String imei_id = queryDeviceDTO.getDeviceInfo().getName();
//
//            int times = 0;
//
//            Calendar calendar = Calendar.getInstance();
//            calendar.setTime(simpleDateFormat.parse(eventTime));
//            calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY)+8);//需要之后解决
//            Timestamp timestamp = new Timestamp(calendar.getTimeInMillis());
//
//            Calendar calendar_now = Calendar.getInstance();
//            Timestamp timestamp_now = new Timestamp(calendar_now.getTimeInMillis());
//
//            Timestamp updateTime = timestamp;
//            Timestamp modify_time_search_local = timestamp_now;
//
//            DeviceInfo deviceInfo = new DeviceInfo(0,device_mac_id,device_id,humidity,status,signalIntensity,statusOfCharge,statusOfLight,temperature,times,imei_id,updateTime,modify_time_search_local);
//
//            deviceInfoList.add(deviceInfo);
//        }
//
//        res.put("deviceInfoList",deviceInfoList);
//        res.put("total",queryDevicesOutDTO.getTotalCount());
//
//        return res;
//    }

//    @Override
//    public Page<DeviceInfo> getAllDeviceInfoFromRemoteService_(Pageable pageable, long userId) throws NorthApiException, ParseException {
//        return deviceInfoDao.getLatestDeviceInfo_all(userId, pageable);
//    }
//
//    @Override
//    public DeviceInfo getDeviceInfoById(long id) {
//        return null;
//    }

    /**
     * 进行远程搜索
     * */
//    @Override
//    public DeviceInfo_detail getDeviceInfoDetailById(long id) throws NorthApiException, ParseException {
//        Device device = deviceService.getDeviceById(Math.toIntExact(id));
//
//        String device_mac_id = device.getMac_id();
//
//        DataCollection dataCollection = new DataCollection(DeviceRemoteSearchUtil.northApiClient);
//        QueryDeviceDataOutDTO queryDeviceDataOutDTO = dataCollection.queryDeviceData(device_mac_id,DeviceRemoteSearchUtil.appId,DeviceRemoteSearchUtil.authOutDTO.getAccessToken());
//
//        ObjectNode node = queryDeviceDataOutDTO.getServices().get(9).getData();
//
//        String eventTime = queryDeviceDataOutDTO.getServices().get(9).getEventTime();
//        eventTime = eventTime.replace("T","");
//        eventTime = eventTime.replace("Z","");
//
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddhhmmss");
//
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(simpleDateFormat.parse(eventTime));
//        calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY)+8);//需要之后解决
//        Timestamp timestamp = new Timestamp(calendar.getTimeInMillis());
//
//        String name = device.getName();
//
//        String attributionOfDevice = device.getAttributionOfDevice();
//        int    organizationId = device.getOrganizationId();
//        String addressOfDevice =  device.getAddressOfDevice();
//
//        int times = 0;
//
//        int signalIntensity = 0;
//
//        try {
//            signalIntensity = queryDeviceDataOutDTO.getServices().get(3).getData().get("signalStrength").asInt();
//        } catch (NullPointerException e){
//            signalIntensity = queryDeviceDataOutDTO.getServices().get(4).getData().get("signalStrength").asInt();
//        }
//
//        int statusOfCharge = node.get("battery").asInt();
//        int statusOfLight = node.get("light").asInt();
//        String temperature = String.format("%.1f",node.get("temperature").asDouble());
//        String humidity = String.format("%.1f",node.get("humidity").asDouble());
//        Date updateTime = timestamp;
//        String imei_id = device.getImei_id();
//
//        DeviceInfo_detail deviceInfo_detail = new DeviceInfo_detail(id,device_mac_id,name,attributionOfDevice,organizationId,addressOfDevice,times,signalIntensity,statusOfCharge,statusOfLight,temperature,humidity,updateTime,imei_id);
//
//        return deviceInfo_detail;
//    }
//
//    @Override
//    public String getOrganizationName(long organizationId) {
//        return null;
//    }

    /**
     * select_mode:
     *      0: 按照24小时, 每一小时一个数据
     *      1: 按照12小时, 每半个小时一个数据, 00:00 点开始
     *      2: 按照12小时, 每半个小时一个数据, 12:00 点开始
     * */
//    @Override
//    public Map<String, Object> getTemperatureRecord(long id, String dateTime, int time_gap) throws Exception {
//        Map<String, Object> map = new HashMap<>();
//
//        int hour_gap = 0;
//
//        if(time_gap == 0){
//            hour_gap = 24;
//        } else {
//            hour_gap = 12;
//        }
//
//        String min_gap = time_gap == 0 ? "DataMinute10" : "DataMinute60";
//
//        DataCollection dataCollection = new DataCollection(DeviceRemoteSearchUtil.northApiClient);
//
//        String device_mac_id = deviceService.getDevice_mac_idById(id);
//
//        QueryDataHistoryInDTO queryDataHistoryInDTO = new QueryDataHistoryInDTO();
//        queryDataHistoryInDTO.setDeviceId(device_mac_id);
//        queryDataHistoryInDTO.setGatewayId(device_mac_id);
//        queryDataHistoryInDTO.setPageNo(0);
//        queryDataHistoryInDTO.setPageSize( hour_gap * 10);//10个为一组
//        queryDataHistoryInDTO.setStartTime(dateTime);
//
//        QueryDataHistoryOutDTO queryDataHistoryOutDTO = dataCollection.queryDataHistory(queryDataHistoryInDTO,DeviceRemoteSearchUtil.appId, DeviceRemoteSearchUtil.authOutDTO.getAccessToken() );
//        List<DeviceDataHistoryDTO> deviceDataHistoryDTOS = queryDataHistoryOutDTO.getDeviceDataHistoryDTOs();
//
//        List<String> temperatureRecord = new LinkedList<>();
//        List<String> timeRecord = new LinkedList<>();
//
//        for(int i = deviceDataHistoryDTOS.size()-1; i > 0;){
//            int temp_index = i;
//            /**
//             * 获取湿度
//             * */
//            if( !deviceDataHistoryDTOS.get(i).getServiceId().equals(min_gap) ){
//                i-=1;
//
//                continue;
//            } else {
//                i-=10;
//            }
//
//            if(temperatureRecord.size() == 24){
//                break;
//            }
//
//            String temperature = "";
//
//            if( time_gap !=0 ){//12小时模式
//                temperature = String.format("%.1f", deviceDataHistoryDTOS.get(temp_index-3).getData().get("temperature").asDouble());
//                temperatureRecord.add(temperature);
//
//                temperature = String.format("%.1f", deviceDataHistoryDTOS.get(temp_index).getData().get("temperature").asDouble());
//                temperatureRecord.add(temperature);
//            } else {
//                temperature = String.format("%.1f", deviceDataHistoryDTOS.get(temp_index).getData().get("temperature").asDouble());
//
//                temperatureRecord.add(temperature);
//            }
//
//            /**
//             * 获取时间
//             * */
//            if(time_gap != 0){// 12小时
//                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd'T'HHmmss'Z'");
//                SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                Calendar calendar = Calendar.getInstance();
//                calendar.setTime( simpleDateFormat.parse( deviceDataHistoryDTOS.get(temp_index).getTimestamp() ) );
//                calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY)+8);
//
//                String dateTime_formmated = simpleDateFormat1.format( calendar.getTime() );
//
//                calendar.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE)-30);
//
//                String dateTime_formmated_2 = simpleDateFormat1.format( calendar.getTime() );
//
//                timeRecord.add(dateTime_formmated_2);
//                timeRecord.add(dateTime_formmated);
//            } else {
//                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd'T'HHmmss'Z'");
//                SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                Calendar calendar = Calendar.getInstance();
//                calendar.setTime( simpleDateFormat.parse( deviceDataHistoryDTOS.get(temp_index).getTimestamp() ) );
//                calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY)+8);
//
//                String dateTime_formmated = simpleDateFormat1.format( calendar.getTime() );
//
//                timeRecord.add(dateTime_formmated);
//            }
//        }
//
//        map.put("temperatureRecord", temperatureRecord);
//        map.put("timeRecord", timeRecord);
//
//        return map;
//    }
//
//    /**
//     * select_mode:
//     *      0: 按照24小时, 每一小时一个数据
//     *      1: 按照12小时, 每半个小时一个数据, 00:00 点开始
//     *      2: 按照12小时, 每半个小时一个数据, 12:00 点开始
//     * */
//    @Override
//    public Map<String, Object> getHumidityRecord(long id, String dateTime, int time_gap) throws Exception {
////        Map<String, Object> map = new HashMap<>();
////
////        DataCollection dataCollection = new DataCollection(DeviceRemoteSearchUtil.northApiClient);
////
////        String device_mac_id = deviceService.getDevice_mac_idById(id);
////
////        QueryDataHistoryInDTO queryDataHistoryInDTO = new QueryDataHistoryInDTO();
////        queryDataHistoryInDTO.setDeviceId(device_mac_id);
////        queryDataHistoryInDTO.setGatewayId(device_mac_id);
////        queryDataHistoryInDTO.setPageNo(0);
////        queryDataHistoryInDTO.setPageSize(24*12);
////        queryDataHistoryInDTO.setStartTime(dateTime);
////
////
////        QueryDataHistoryOutDTO queryDataHistoryOutDTO = dataCollection.queryDataHistory(queryDataHistoryInDTO,DeviceRemoteSearchUtil.appId, DeviceRemoteSearchUtil.authOutDTO.getAccessToken() );
////
////        List<DeviceDataHistoryDTO> deviceDataHistoryDTOS = queryDataHistoryOutDTO.getDeviceDataHistoryDTOs();
////
////        List<String> humidityRecord = new LinkedList<>();
////        List<String> timeRecord = new LinkedList<>();
////
////        for(int i = deviceDataHistoryDTOS.size()-1; i > 0;){
////            int temp_index = i;
////
////            /**
////             * 获取湿度
////             * */
////
////            if( !deviceDataHistoryDTOS.get(i).getServiceId().equals("DataMinute10") ){
////                i-=1;
////                continue;
////            } else {
////                i-=10;
////            }
////
////            if(humidityRecord.size() == 24){
////                break;
////            }
////
////            String humidity = String.format("%.1f", deviceDataHistoryDTOS.get(temp_index).getData().get("humidity").asDouble());
////            humidityRecord.add(humidity);
////
////            /**
////             * 获取时间
////             * */
////            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd'T'HHmmss'Z'");
////            SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
////            Calendar calendar = Calendar.getInstance();
////            calendar.setTime( simpleDateFormat.parse( deviceDataHistoryDTOS.get(temp_index).getTimestamp() ) );
////            calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY)+8);
////
////            String dateTime_formmated = simpleDateFormat1.format( calendar.getTime() );
////
////            timeRecord.add(dateTime_formmated);
////        }
////
////        map.put("humidityRecord", humidityRecord);
////        map.put("timeRecord", timeRecord);
//        Map<String, Object> map = new HashMap<>();
//
//        int hour_gap = 0;
//
//        if(time_gap == 0){
//            hour_gap = 24;
//        } else {
//            hour_gap = 12;
//        }
//
//        String min_gap = time_gap == 0 ? "DataMinute10" : "DataMinute60";
//
//        DataCollection dataCollection = new DataCollection(DeviceRemoteSearchUtil.northApiClient);
//
//        String device_mac_id = deviceService.getDevice_mac_idById(id);
//
//        QueryDataHistoryInDTO queryDataHistoryInDTO = new QueryDataHistoryInDTO();
//        queryDataHistoryInDTO.setDeviceId(device_mac_id);
//        queryDataHistoryInDTO.setGatewayId(device_mac_id);
//        queryDataHistoryInDTO.setPageNo(0);
//        queryDataHistoryInDTO.setPageSize( hour_gap * 10);//10个为一组
//        queryDataHistoryInDTO.setStartTime(dateTime);
//
//        QueryDataHistoryOutDTO queryDataHistoryOutDTO = dataCollection.queryDataHistory(queryDataHistoryInDTO,DeviceRemoteSearchUtil.appId, DeviceRemoteSearchUtil.authOutDTO.getAccessToken() );
//        List<DeviceDataHistoryDTO> deviceDataHistoryDTOS = queryDataHistoryOutDTO.getDeviceDataHistoryDTOs();
//
//        List<String> humidityRecord = new LinkedList<>();
//        List<String> timeRecord = new LinkedList<>();
//
//        for(int i = deviceDataHistoryDTOS.size()-1; i > 0;){
//            int temp_index = i;
//            /**
//             * 获取湿度
//             * */
//            if( !deviceDataHistoryDTOS.get(i).getServiceId().equals(min_gap) ){
//                i-=1;
//
//                continue;
//            } else {
//                i-=10;
//            }
//
//            if(humidityRecord.size() == 24){
//                break;
//            }
//
//            String temperature = "";
//
//            if( time_gap !=0 ){//12小时模式
//                temperature = String.format("%.1f", deviceDataHistoryDTOS.get(temp_index-3).getData().get("humidity").asDouble());
//                humidityRecord.add(temperature);
//
//                temperature = String.format("%.1f", deviceDataHistoryDTOS.get(temp_index).getData().get("humidity").asDouble());
//                humidityRecord.add(temperature);
//            } else {
//                temperature = String.format("%.1f", deviceDataHistoryDTOS.get(temp_index).getData().get("humidity").asDouble());
//
//                humidityRecord.add(temperature);
//            }
//
//            /**
//             * 获取时间
//             * */
//            if(time_gap != 0){// 12小时
//                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd'T'HHmmss'Z'");
//                SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                Calendar calendar = Calendar.getInstance();
//                calendar.setTime( simpleDateFormat.parse( deviceDataHistoryDTOS.get(temp_index).getTimestamp() ) );
//                calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY)+8);
//
//                String dateTime_formmated = simpleDateFormat1.format( calendar.getTime() );
//
//                calendar.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE)-30);
//
//                String dateTime_formmated_2 = simpleDateFormat1.format( calendar.getTime() );
//
//                timeRecord.add(dateTime_formmated_2);
//                timeRecord.add(dateTime_formmated);
//            } else {
//                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd'T'HHmmss'Z'");
//                SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                Calendar calendar = Calendar.getInstance();
//                calendar.setTime( simpleDateFormat.parse( deviceDataHistoryDTOS.get(temp_index).getTimestamp() ) );
//                calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY)+8);
//
//                String dateTime_formmated = simpleDateFormat1.format( calendar.getTime() );
//
//                timeRecord.add(dateTime_formmated);
//            }
//        }
//
//        map.put("humidityRecord", humidityRecord);
//        map.put("timeRecord", timeRecord);
//
//
//        return map;
//    }



}
