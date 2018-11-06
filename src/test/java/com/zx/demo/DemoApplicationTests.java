package com.zx.demo;

import com.huawei.iotplatform.client.NorthApiException;
import com.huawei.iotplatform.client.dto.DeviceDataHistoryDTO;
import com.huawei.iotplatform.client.dto.QueryDataHistoryInDTO;
import com.huawei.iotplatform.client.dto.QueryDataHistoryOutDTO;
import com.huawei.iotplatform.client.dto.QueryDeviceDataOutDTO;
import com.huawei.iotplatform.client.invokeapi.DataCollection;
import com.zx.demo.domain.*;
import com.zx.demo.repository.AdminDao;
import com.zx.demo.repository.DeviceDao;
import com.zx.demo.repository.DeviceInfoDao;
import com.zx.demo.repository.TempDao;
import com.zx.demo.service.DeviceInfoSearchService;
import com.zx.demo.service.DeviceInfoService;
import com.zx.demo.util.DeviceRemoteSearchUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.management.Query;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

//    @Autowired
//    private UserDao userDao;


    @Autowired
    private AdminDao adminDao;

    @Autowired
    private DeviceInfoService deviceInfoService;

    @Autowired
    private DeviceInfoDao deviceInfoDao;

    @Autowired
    DeviceInfoSearchService deviceInfoSearchService;


//    @Autowired
//    private DeviceInfoSearchService deviceInfoSearchService;


    @Test
    public void contextLoads() {
    }

    @Test
    public void testAdd(){
        User user = new User();


//        userDao.save(user);
    }

    @Test
    public void testAddAdmin(){
        Admin admin = new Admin();


        adminDao.save(admin);
    }

    @Test
    public void addDevice(){

    }

    @Test
    public void search(){

    }


    @Autowired
    private DeviceDao deviceDao;

    @Autowired
    private TempDao tempDao;

    @Test
    public void get() throws NorthApiException, ParseException {
//        DeviceRemoteSearchUtil.initRemoteServer();
//
//        DataCollection dataCollection = new DataCollection(DeviceRemoteSearchUtil.northApiClient);
//
//        QueryDataHistoryInDTO queryDataHistoryInDTO = new QueryDataHistoryInDTO();
//        queryDataHistoryInDTO.setDeviceId("5bc1e78a-e190-4037-884f-07f8d4c500a0");
//        queryDataHistoryInDTO.setGatewayId("5bc1e78a-e190-4037-884f-07f8d4c500a0");
//        queryDataHistoryInDTO.setPageNo(0);
//        queryDataHistoryInDTO.setPageSize(24);
//        queryDataHistoryInDTO.setStartTime("20181025T160000Z");
//
//        QueryDataHistoryOutDTO queryDataHistoryOutDTO = dataCollection.queryDataHistory(queryDataHistoryInDTO, DeviceRemoteSearchUtil.appId,DeviceRemoteSearchUtil.authOutDTO.getAccessToken());
//
//        for( DeviceDataHistoryDTO deviceDataHistoryDTO : queryDataHistoryOutDTO.getDeviceDataHistoryDTOs() ){
//            deviceDataHistoryDTO.getData();
//        }
        DeviceRemoteSearchUtil.initRemoteServer();


//        deviceInfoSearchService.getIEMENumberByDeviceId("bced3021-1516-4535-b4f7-607a75435e9c");
//        deviceInfoSearchService.getIEMENumberByDeviceId("72bbc6b0-b5a8-4d36-a0bf-a467671f5d5f");
//        deviceInfoSearchService.getIEMENumberByDeviceId("853f83b8-907d-4633-8f63-bc259fc4b70a");
//        deviceInfoSearchService.getIEMENumberByDeviceId("523a54c7-003e-482d-acba-6f95178821a8");
//        deviceInfoSearchService.getIEMENumberByDeviceId("017d80be-2c8f-47b7-8bc7-fabb8e04d83b");
//
//        DataCollection collection = new DataCollection(DeviceRemoteSearchUtil.northApiClient);
//
//        QueryDataHistoryOutDTO queryDataHistoryOutDTO;
//
//        QueryDataHistoryInDTO queryDataHistoryInDTO = new QueryDataHistoryInDTO();
//
//        queryDataHistoryInDTO.setDeviceId("853f83b8-907d-4633-8f63-bc259fc4b70a");
//        queryDataHistoryInDTO.setGatewayId("853f83b8-907d-4633-8f63-bc259fc4b70a");
//        queryDataHistoryInDTO.setStartTime("20181030T000000Z");
//        queryDataHistoryInDTO.setPageNo(0);
//        queryDataHistoryInDTO.setPageSize( Integer.MAX_VALUE );//10个为一组
//        queryDataHistoryInDTO.setStartTime("20181029T000000Z");

        String name = getName("bced3021-1516-4535-b4f7-607a75435e9c");
        System.out.println(name);
        name = getName("72bbc6b0-b5a8-4d36-a0bf-a467671f5d5f");
        System.out.println(name);
        name = getName("853f83b8-907d-4633-8f63-bc259fc4b70a");
        System.out.println(name);
        name = getName("523a54c7-003e-482d-acba-6f95178821a8");
        System.out.println(name);
        name = getName("017d80be-2c8f-47b7-8bc7-fabb8e04d83b");
        System.out.println(name);

//
//        queryDataHistoryOutDTO = collection.queryDataHistory(queryDataHistoryInDTO,DeviceRemoteSearchUtil.appId,DeviceRemoteSearchUtil.authOutDTO.getAccessToken());
//
//        List<DeviceDataHistoryDTO> data = queryDataHistoryOutDTO.getDeviceDataHistoryDTOs();
//        List<Temp> tempList = new LinkedList<>();
//
//        for(int i = data.size()-1; i>=0; i--){
//            DeviceDataHistoryDTO temp  = data.get(i);
//
//            if(temp.getServiceId().equals("DataMinute10")){
//                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd'T'HHmmss'Z'");
//                SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                Calendar calendar = Calendar.getInstance();
//                calendar.setTime( simpleDateFormat.parse( temp.getTimestamp() ) ) ;
//                calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY)+8);
//                calendar.set(Calendar.MINUTE,10);
//
//                tempList.add( new Temp(name, temp.getData().get("temperature").asText(), simpleDateFormat1.format( calendar.getTime() ) ) );
//
//            }
//
//            if(temp.getServiceId().equals("DataMinute20")){
//                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd'T'HHmmss'Z'");
//                SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                Calendar calendar = Calendar.getInstance();
//                calendar.setTime( simpleDateFormat.parse( temp.getTimestamp() ) ) ;
//                calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY)+8);
//                calendar.set(Calendar.MINUTE,20);
//
//                tempList.add( new Temp(name, temp.getData().get("temperature").asText(), simpleDateFormat1.format( calendar.getTime() ) ) );
//
//            }
//
//            if(temp.getServiceId().equals("DataMinute30")){
//                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd'T'HHmmss'Z'");
//                SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                Calendar calendar = Calendar.getInstance();
//                calendar.setTime( simpleDateFormat.parse( temp.getTimestamp() ) ) ;
//                calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY)+8);
//                calendar.set(Calendar.MINUTE,30);
//
//                tempList.add( new Temp(name, temp.getData().get("temperature").asText(), simpleDateFormat1.format( calendar.getTime() ) ) );
//
//            }
//
//            if(temp.getServiceId().equals("DataMinute40")){
//                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd'T'HHmmss'Z'");
//                SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                Calendar calendar = Calendar.getInstance();
//                calendar.setTime( simpleDateFormat.parse( temp.getTimestamp() ) ) ;
//                calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY)+8);
//                calendar.set(Calendar.MINUTE,40);
//
//                tempList.add( new Temp(name, temp.getData().get("temperature").asText(), simpleDateFormat1.format( calendar.getTime() ) ) );
//
//            }
//
//            if(temp.getServiceId().equals("DataMinute50")){
//                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd'T'HHmmss'Z'");
//                SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                Calendar calendar = Calendar.getInstance();
//                calendar.setTime( simpleDateFormat.parse( temp.getTimestamp() ) ) ;
//                calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY)+8);
//                calendar.set(Calendar.MINUTE,50);
//
//                tempList.add( new Temp(name, temp.getData().get("temperature").asText(), simpleDateFormat1.format( calendar.getTime() ) ) );
//
//            }
//
//            if(temp.getServiceId().equals("DataMinute60")){
//                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd'T'HHmmss'Z'");
//                SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                Calendar calendar = Calendar.getInstance();
//                calendar.setTime( simpleDateFormat.parse( temp.getTimestamp() ) ) ;
//                calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY)+8);
//                calendar.set(Calendar.MINUTE,60);
//
//                tempList.add( new Temp(name, temp.getData().get("temperature").asText(), simpleDateFormat1.format( calendar.getTime() ) ) );
//
//            }
//        }
//
//
//        for(Temp temp : tempList){
//            tempDao.save(temp);
//        }
//
//
//        System.out.println("aa");

    }

    public String getName(String deviceId){
        DataCollection collection = new DataCollection(DeviceRemoteSearchUtil.northApiClient);

        QueryDeviceDataOutDTO queryDevicesOutDTO = null;
        try {
            queryDevicesOutDTO = collection.queryDeviceData(deviceId, DeviceRemoteSearchUtil.appId, DeviceRemoteSearchUtil.authOutDTO.getAccessToken());

            return queryDevicesOutDTO.getDeviceInfo().getName();
        } catch (NorthApiException e) {
            e.printStackTrace();
        }

        return "";
    }


    @Test
    public void refresh(){

        try {
            DeviceRemoteSearchUtil.initRemoteServer();

            deviceInfoSearchService.refreshDevicesInfoFromRemoteServer_();
        } catch (NorthApiException e) {
            e.printStackTrace();
        }
    }
}
