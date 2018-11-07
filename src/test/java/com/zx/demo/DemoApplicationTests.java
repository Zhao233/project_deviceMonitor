package com.zx.demo;

import com.huawei.iotplatform.client.NorthApiException;
import com.huawei.iotplatform.client.dto.DeviceDataHistoryDTO;
import com.huawei.iotplatform.client.dto.QueryDataHistoryInDTO;
import com.huawei.iotplatform.client.dto.QueryDataHistoryOutDTO;
import com.huawei.iotplatform.client.dto.QueryDeviceDataOutDTO;
import com.huawei.iotplatform.client.invokeapi.DataCollection;
import com.zx.demo.domain.*;
import com.zx.demo.model.DeviceInfo_all;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    public void get() {
        Pageable pageable = new PageRequest(0, 50, new Sort(Sort.Direction.DESC, "id"));

        Page<DeviceInfo_all> page = deviceInfoDao.getLatestDeviceInfo_all(pageable,0);

        System.out.println("s");
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
