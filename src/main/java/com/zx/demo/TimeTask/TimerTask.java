package com.zx.demo.TimeTask;

import com.zx.demo.service.DeviceInfoSearchService;
import com.zx.demo.util.DeviceRemoteSearchUtil;
import com.zx.demo.util_api.HttpsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;

@Component
public class TimerTask {
    @Autowired
    private DeviceInfoSearchService deviceInfoSearchService;

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Scheduled(cron = "0 1 * * * ? ")
    public void getTask1() throws Exception {//每到小时整点查询一次，向数据库中放入两条记录
        System.out.println("update data from remote server");

        deviceInfoSearchService.refreshDevicesInfoFromRemoteServer_();
    }

    /*@Scheduled(cron = "0 0/5 * * * *")
    public void getTask2() {//每5分钟更新一次token
        try {
            System.out.println("refresh token");

            HttpsUtil httpsUtil = new HttpsUtil();
            httpsUtil.initSSLConfigForTwoWay();

            //DeviceRemoteSearchUtil.getRefreshToken( httpsUtil );

        }catch (Exception e) {
            e.printStackTrace();
        }
    }*/

}
