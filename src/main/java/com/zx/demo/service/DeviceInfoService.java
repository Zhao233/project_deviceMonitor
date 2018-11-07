package com.zx.demo.service;

import com.huawei.iotplatform.client.NorthApiException;
import com.zx.demo.domain.DeviceInfo;
import com.zx.demo.model.DeviceInfo_all;
import com.zx.demo.model.DeviceInfo_detail;
import com.zx.demo.model.DeviceInfo_saveToDataBase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

public interface DeviceInfoService {

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

    Page<DeviceInfo_all> getDeviceInfo_all(Pageable pageable, long userId);
    DeviceInfo_detail getDeviceInfo_detail(Pageable pageable, int deviceId);

    Map<String, Object> getTemperatureRecord_Local(long id, String dateTime, int select_mode);

    Map<String, Object> getHumidityRecord_Local(long id, String dateTime, int select_mode);

    int getSearchTimes(String device_mac_id);

    void addDeviceInfo(DeviceInfo deviceInfo);
}
