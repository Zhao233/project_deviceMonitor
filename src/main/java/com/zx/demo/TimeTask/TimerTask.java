package com.zx.demo.TimeTask;

import com.huawei.iotplatform.client.NorthApiException;
import com.zx.demo.service.DeviceInfoSearchService;
import com.zx.demo.util.DeviceRemoteSearchUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class TimerTask {
    @Autowired
    private DeviceInfoSearchService deviceInfoSearchService;

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

//    @Scheduled(cron = "5 0 * * * ? *")
    @Scheduled(cron = "59 0/30 * * * ?")
    public void getTask1() throws NorthApiException {//每到半点，向数据库中查询设备信息，存入数据库中
        System.out.println("update data from remote server");

        deviceInfoSearchService.refreshDevicesInfoFromRemoteServer();
    }

    @Scheduled(cron = "0 0/15 * * * *")
    public void getTask2() {//半点刷新一次token
        try {
            System.out.println("refresh token");

            DeviceRemoteSearchUtil.refreshToken();

        } catch (NorthApiException e) {
            e.printStackTrace();
        }
    }

}
