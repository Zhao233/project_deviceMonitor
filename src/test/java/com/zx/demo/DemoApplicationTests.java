package com.zx.demo;

import com.huawei.iotplatform.client.NorthApiException;
import com.huawei.iotplatform.client.dto.DeviceDataHistoryDTO;
import com.huawei.iotplatform.client.dto.QueryDataHistoryInDTO;
import com.huawei.iotplatform.client.dto.QueryDataHistoryOutDTO;
import com.huawei.iotplatform.client.dto.QueryDeviceDataOutDTO;
import com.huawei.iotplatform.client.invokeapi.DataCollection;
import com.zx.demo.domain.Admin;
import com.zx.demo.domain.Device;
import com.zx.demo.domain.DeviceInfo;
import com.zx.demo.domain.User;
import com.zx.demo.repository.AdminDao;
import com.zx.demo.repository.DeviceDao;
import com.zx.demo.repository.DeviceInfoDao;
import com.zx.demo.service.DeviceInfoSearchService;
import com.zx.demo.service.DeviceInfoService;
import com.zx.demo.util.DeviceRemoteSearchUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Date;
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

    @Test
    public void get() throws NorthApiException {
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

        deviceInfoSearchService.refreshDevicesInfoFromRemoteServer();

//        DeviceInfo deviceInfo = deviceInfoSearchService.getDeviceInfoFromRemoteServer("5bc1e78a-e190-4037-884f-07f8d4c500a0");
//
//        deviceInfoService.addDeviceInfo(deviceInfo);
//
//        System.out.println("asdfasdfasdf");

    }
}
