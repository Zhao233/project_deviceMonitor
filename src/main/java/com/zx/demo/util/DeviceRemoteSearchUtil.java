package com.zx.demo.util;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.huawei.iotplatform.client.NorthApiClient;
import com.huawei.iotplatform.client.NorthApiException;
import com.huawei.iotplatform.client.dto.*;
import com.huawei.iotplatform.client.invokeapi.Authentication;
import com.zx.demo.domain.DeviceInfo;
import com.zx.demo.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

//import com.huawei.iotplatform.client.dto.DeviceInfo;

public class DeviceRemoteSearchUtil {
    public static NorthApiClient northApiClient = null;
    public static ClientInfo         clientInfo = null;
    public static Authentication authentication = null;
    public static AuthOutDTO         authOutDTO = null;


    public static final int POSITION_DATA_1 = 1;//六组数据中取第二组
    public static final int POSITION_DATA_2 = 4;//六组数据中取第

//    public static String                  appId = "wukHyW0OkG2xPbShcsev3zolbfEa";
//    public static String                 secret = "8CYF7_Nx7IaoLM2jB1mzcMMyA3Ya";
    public static String                  appId = "RLw165YDYe0X_GtouiMLbNF8sZUa";
    public static String                 secret = "CvQucLCY1QaDz7YEpt4TKY3xlawa";

    public static void initRemoteServer() throws NorthApiException {
        northApiClient = new NorthApiClient();
        clientInfo = new ClientInfo();

        clientInfo.setAppId(appId);
        clientInfo.setSecret(secret);

        clientInfo.setPlatformIp("180.101.147.89");
        clientInfo.setPlatformPort("8743");

        northApiClient.setClientInfo(clientInfo);

        northApiClient.initSSLConfig();

        authentication = new Authentication();
        authentication.setNorthApiClient(northApiClient);

        authOutDTO = authentication.getAuthToken();
    }

    public static void refreshToken() throws NorthApiException {
        //authOutDTO = authentication.getAuthToken();

        AuthRefreshInDTO authRefreshInDTO = new AuthRefreshInDTO();
        authRefreshInDTO.setAppId(clientInfo.getAppId());
        authRefreshInDTO.setSecret(clientInfo.getSecret());
        authRefreshInDTO.setRefreshToken(authOutDTO.getRefreshToken());

        AuthRefreshOutDTO out = authentication.refreshAuthToken(authRefreshInDTO);

        authOutDTO.setRefreshToken( out.getRefreshToken() );
        authOutDTO.setAccessToken( out.getAccessToken() );
        authOutDTO.setExpiresIn( out.getExpiresIn() );
        authOutDTO.setTokenType( out.getTokenType() );
    }


public static DeviceInfo getDeviceInfo(int searchTimes, QueryDeviceDTO deviceDTO, HashMap<String, Long> cache_device_mac_id) throws ParseException {
    com.huawei.iotplatform.client.dto.DeviceInfo deviceInfo = deviceDTO.getDeviceInfo();

    System.out.println("size of service :"+deviceDTO.getServices().size());
    System.out.println("deviceDTO :"+deviceDTO.getServices().get(0).getServiceId());
    System.out.println();

    if(deviceDTO.getServices().size() == 1){
        return null;
    }

    ObjectNode objectNode_2 = deviceDTO.getServices().get(9).getData();

    String eventTime = deviceDTO.getServices().get(9).getEventTime();
    eventTime = eventTime.replace("T","");
    eventTime = eventTime.replace("Z","");

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddhhmmss");

    //第一个deviceInfo
    Calendar calendar_2 = Calendar.getInstance();
    calendar_2.setTime(simpleDateFormat.parse(eventTime));
    calendar_2.set(Calendar.HOUR_OF_DAY, calendar_2.get(Calendar.HOUR_OF_DAY)+8);
    Timestamp timestamp_2 = new Timestamp(calendar_2.getTimeInMillis());

    String humidity_2 = String.valueOf(objectNode_2.get("humidity").asDouble());

    String temperature_2 =String.format("%.1f", objectNode_2.get("temperature").asDouble());

    int light_2 = objectNode_2.get("light").asInt();
    int battery_2 = objectNode_2.get("battery").asInt();

    Calendar calendar_now = Calendar.getInstance();
    calendar_now.set(Calendar.HOUR_OF_DAY,calendar_now.get(Calendar.HOUR_OF_DAY));
    Timestamp timestamp_now = new Timestamp(calendar_now.getTimeInMillis());

    deviceInfo.getName();

//    DeviceInfo deviceInfo_1 = new DeviceInfo(deviceDTO.getDeviceId(), Math.toIntExact( cache_device_mac_id.get( deviceDTO.getDeviceId() ) ),"","","","", humidity_2, deviceDTO.getDeviceInfo().getStatus(), deviceDTO.getDeviceInfo().getSignalStrength(), battery_2, light_2, temperature_2, searchTimes, timestamp_2, timestamp_now);

    return null;
}

public static DeviceInfo[] getDeviceInfo(QueryDeviceDataOutDTO queryDeviceDataOutDTO) {
        DeviceInfo[] deviceInfos = new DeviceInfo[2];

        com.huawei.iotplatform.client.dto.DeviceInfo deviceInfo = queryDeviceDataOutDTO.getDeviceInfo();

        return  deviceInfos;
    }
}