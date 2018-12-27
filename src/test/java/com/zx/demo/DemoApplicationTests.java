package com.zx.demo;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.huawei.iotplatform.client.NorthApiException;
import com.huawei.iotplatform.client.dto.DeviceDataHistoryDTO;
import com.huawei.iotplatform.client.dto.QueryDataHistoryInDTO;
import com.huawei.iotplatform.client.dto.QueryDataHistoryOutDTO;
import com.huawei.iotplatform.client.dto.QueryDeviceDataOutDTO;
import com.huawei.iotplatform.client.invokeapi.DataCollection;
import com.zx.demo.domain.*;
import com.zx.demo.model.DeviceInfo_all;
import com.zx.demo.model.Test1;
import com.zx.demo.repository.*;
import com.zx.demo.service.ConfigService;
import com.zx.demo.service.DeviceInfoSearchService;
import com.zx.demo.service.DeviceInfoService;
import com.zx.demo.util.DecodeUtil;
import com.zx.demo.util.DeviceRemoteSearchUtil;
import com.zx.demo.util.PasswordEncodeAssistant;
import net.bytebuddy.dynamic.scaffold.MethodGraph;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;
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
    @Autowired
    private DeviceInfoService deviceInfoService;

    @Autowired
    private UserDao userDao;

    @Autowired
    private DeviceInfoDao deviceInfoDao;

    @Autowired
    private DeviceInfoSearchService deviceInfoSearchService;

    @Test
    public void Test() {
        List<DeviceInfo_all> all = (List<DeviceInfo_all>) deviceInfoService.getDeviceInfo_all_normal(50, 0, "", "", 0, "").get("rows");

        System.out.println("ss");
//        List<DeviceInfo_all> deviceInfo_alls = deviceInfoDao.getLatestDeviceInfo_all_abnormal(0,"");
//
//        deviceInfo_alls = sort(deviceInfo_alls,"temperature","asc");
//
//        for(DeviceInfo_all deviceInfo_all : deviceInfo_alls){
//            System.out.println(deviceInfo_all.getTemperature());
//        }
    }

    private List<DeviceInfo_all> sort(List<DeviceInfo_all> list, String property, String order) {
        int len = list.size();

        double value = 0;

        for (int i = 0; i < len; i++) {//循环次数
            switch (property) {
                case "temperature":
                    value = list.get(i).getTemperature();

                    break;
                case "humidity":
                    value = list.get(i).getHumidity();
                    break;
            }

            int position = i;

            for (int j = i + 1; j < len; j++) {//找到最小的值和位置
                double value_ = 0;

                switch (property) {
                    case "temperature":
                        value_ = list.get(j).getTemperature();

                        break;
                    case "humidity":
                        value_ = list.get(j).getHumidity();
                        break;
                }

                if (order.equals("desc")) {
                    if (value_ >= value) {
                        value = value_;
                        position = j;
                    }
                } else {
                    if (value_ <= value) {
                        value = value_;
                        position = j;
                    }
                }
            }

            DeviceInfo_all temp = list.get(position);

            list.set(position, list.get(i));
            list.set(i, temp);
        }

        return list;
    }

    //
////    @Autowired
////    private UserDao userDao;
//
//
//    @Autowired
//    private AdminDao adminDao;
//
//
//
//    @Autowired
//    private DeviceInfoDao deviceInfoDao;
//
//    @Autowired
//    DeviceInfoSearchService deviceInfoSearchService;
//
//
////    @Autowired
////    private DeviceInfoSearchService deviceInfoSearchService;
//
//
//    @Test
//    public void contextLoads() {
//    }
//
//    @Test
//    public void testAdd(){
//        User user = new User();
//
//
////        userDao.save(user);
//    }
//
//    @Test
//    public void testAddAdmin(){
//        Admin admin = new Admin();
//
//
//        adminDao.save(admin);
//    }
//
//    @Test
//    public void addDevice(){
//
//    }
//
//    @Test
//    public void search(){
//
//    }
//
//
//    @Autowired
//    private DeviceDao deviceDao;
//
//    @Autowired
//    private TempDao tempDao;
//
//    @Test
//    public void get() {
//        Pageable pageable = new PageRequest(0, 50, new Sort(Sort.Direction.DESC, "id"));
//
////        Page<DeviceInfo_all> page = deviceInfoDao.getLatestDeviceInfo_all(pageable,0);
//
//        System.out.println("s");
//    }
//
    public String getName(String deviceId) {
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

    //
//
//    @Test
//    public void refresh(){
//        try {
//            DeviceRemoteSearchUtil.initRemoteServer();
//
//            deviceInfoSearchService.refreshDevicesInfoFromRemoteServer_();
//        } catch (NorthApiException e) {
//            e.printStackTrace();
//        }
//    }
//
    @Autowired
    TempDao tempDao;

    @Autowired
    DeviceInfoDao dao;

    @Test
    public void getInfo() throws NorthApiException, ParseException {
        DeviceRemoteSearchUtil.initRemoteServer();

        DataCollection dataCollection = new DataCollection(DeviceRemoteSearchUtil.northApiClient);

        String deviceId_4 = "853f83b8-907d-4633-8f63-bc259fc4b70a";//T-3
//        String deviceId_4 = "72bbc6b0-b5a8-4d36-a0bf-a467671f5d5f";//T-4
//        String deviceId_4 = "bced3021-1516-4535-b4f7-607a75435e9c";//T-5

        Calendar calendar4 = Calendar.getInstance();
        calendar4.set(Calendar.DAY_OF_MONTH, 24);
        calendar4.set(Calendar.HOUR_OF_DAY, 0);
        calendar4.set(Calendar.MINUTE, 0);
        calendar4.set(Calendar.SECOND, 0);

        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("YYYYMMdd'T'HHmmss'Z'");
        String time2 = simpleDateFormat2.format(calendar4.getTime());

        QueryDataHistoryInDTO queryDataHistoryInDTO = new QueryDataHistoryInDTO();
        queryDataHistoryInDTO.setDeviceId(deviceId_4);
        queryDataHistoryInDTO.setGatewayId(deviceId_4);
        queryDataHistoryInDTO.setPageNo(0);
        queryDataHistoryInDTO.setPageSize(Integer.MAX_VALUE);
        queryDataHistoryInDTO.setStartTime(time2);

        QueryDataHistoryOutDTO queryDataHistoryOutDTO = dataCollection.queryDataHistory(queryDataHistoryInDTO, DeviceRemoteSearchUtil.appId, DeviceRemoteSearchUtil.authOutDTO.getAccessToken());

        String name = getName(deviceId_4);

        LinkedList<Temp> list = new LinkedList<>();

        for (int i = queryDataHistoryOutDTO.getDeviceDataHistoryDTOs().size() - 1; i >= 0; i--) {
            DeviceDataHistoryDTO dto = queryDataHistoryOutDTO.getDeviceDataHistoryDTOs().get(i);

            if (dto.getServiceId().equals("DataMinute10")) {
                ObjectNode objectNode = dto.getData();

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd'T'HHmmss'Z'");
                SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = simpleDateFormat.parse(dto.getTimestamp());

                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) + 8);
                calendar.set(Calendar.MINUTE, 10);

                String Time = simpleDateFormat1.format(calendar.getTime());

                String temperature = objectNode.get("temperature").asText();

                Temp temp = new Temp(name, temperature, Time);
                list.add(temp);
            }

            if (dto.getServiceId().equals("DataMinute20")) {
                ObjectNode objectNode = dto.getData();

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd'T'HHmmss'Z'");
                SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = simpleDateFormat.parse(dto.getTimestamp());

                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) + 8);
                calendar.set(Calendar.MINUTE, 20);

                String Time = simpleDateFormat1.format(calendar.getTime());

                String temperature = objectNode.get("temperature").asText();

                Temp temp = new Temp(name, temperature, Time);
                list.add(temp);
            }

            if (dto.getServiceId().equals("DataMinute30")) {
                ObjectNode objectNode = dto.getData();

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd'T'HHmmss'Z'");
                SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = simpleDateFormat.parse(dto.getTimestamp());

                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) + 8);
                calendar.set(Calendar.MINUTE, 30);

                String Time = simpleDateFormat1.format(calendar.getTime());

                String temperature = objectNode.get("temperature").asText();

                Temp temp = new Temp(name, temperature, Time);
                list.add(temp);
            }

            if (dto.getServiceId().equals("DataMinute40")) {
                ObjectNode objectNode = dto.getData();

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd'T'HHmmss'Z'");
                SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = simpleDateFormat.parse(dto.getTimestamp());

                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) + 8);
                calendar.set(Calendar.MINUTE, 40);

                String Time = simpleDateFormat1.format(calendar.getTime());

                String temperature = objectNode.get("temperature").asText();

                Temp temp = new Temp(name, temperature, Time);
                list.add(temp);
            }

            if (dto.getServiceId().equals("DataMinute50")) {
                ObjectNode objectNode = dto.getData();

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd'T'HHmmss'Z'");
                SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = simpleDateFormat.parse(dto.getTimestamp());

                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) + 8);
                calendar.set(Calendar.MINUTE, 50);

                String Time = simpleDateFormat1.format(calendar.getTime());

                String temperature = objectNode.get("temperature").asText();

                Temp temp = new Temp(name, temperature, Time);
                list.add(temp);
            }

            if (dto.getServiceId().equals("DataMinute60")) {
                ObjectNode objectNode = dto.getData();

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd'T'HHmmss'Z'");
                SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = simpleDateFormat.parse(dto.getTimestamp());

                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) + 8);
                calendar.set(Calendar.MINUTE, 60);

                String Time = simpleDateFormat1.format(calendar.getTime());

                String temperature = objectNode.get("temperature").asText();

                Temp temp = new Temp(name, temperature, Time);
                list.add(temp);
            }

        }

        for (Temp temp : list) {
            //tempDao.save(temp);
        }
    }


    @Test
    public void test() throws NorthApiException {
        deviceInfoSearchService.refreshDevicesInfoFromRemoteServer_();
    }
}
//
//    @Test
//    public void testInsertDeviceInfo(){
//        DeviceInfo deviceInfo = new DeviceInfo(0,"",100,"","","",1,0,"",0,"",new Timestamp(new Date().getTime()),new Timestamp(new Date().getTime()));
//
//        deviceInfoDao.save(deviceInfo);
//    }
//
//    @Autowired
//    private ConfigService configService;
//
//    @Autowired
//    private ConfigDao configDao;
//1
//    @Test
//    public void testConfig(){
////        Config config = new Config();
////        config.setTemperature_upper_limit("30");
////        config.setTemperature_lower_limit("10");
////        config.setHumidity_upper_limit("70");
////        config.setHumidity_lower_limit("10");
////
////        configService.save(config);
//
//        Config config = configService.getConfig(Long.valueOf(0));
//
//        System.out.println("asdfasd");
//    }
//
//    @Test
//    public void testDeviceInfo(){
//        Pageable pageable = new PageRequest(0, 50, Sort.Direction.DESC, "id");
//
//        Page<DeviceInfo_all> page = deviceInfoDao.getLatestDeviceInfo_all_nomal(pageable,0);
//
//        System.out.println("");
//    }
//}
