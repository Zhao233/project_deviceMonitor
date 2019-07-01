package com.zx.demo.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.huawei.iotplatform.client.NorthApiClient;
import com.huawei.iotplatform.client.NorthApiException;
import com.huawei.iotplatform.client.dto.*;
import com.huawei.iotplatform.client.invokeapi.Authentication;

import java.util.*;

import com.zx.demo.domain.DeviceInfo;
import com.zx.demo.domain.User;
import com.zx.demo.service.UserService;
import com.zx.demo.util_api.Constant;
import com.zx.demo.util_api.HttpsUtil;
import com.zx.demo.util_api.JsonUtil;
import com.zx.demo.util_api.StreamClosedHttpResponse;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

//import com.huawei.iotplatform.client.dto.DeviceInfo;

public class DeviceRemoteSearchUtil {
    //public static String accessToken = "";

    public static JsonNode getResult(String device_mac_Id) throws Exception {
//        if(accessToken == ""){
//            return null;
//        }

        String appId = "";

        JsonNode node = null;

        HttpsUtil httpsUtil = new HttpsUtil();
        httpsUtil.initSSLConfigForTwoWay();

        String accessToken = login(httpsUtil,"","");

        String urlQuerySpecifyDevice = Constant.QUERY_SPECIFY_DEVICE + "/" + device_mac_Id;

        Map<String, String> paramQuerySpecifyDevice = new HashMap<>();
        paramQuerySpecifyDevice.put("appId", appId);

        Map<String, String> header = new HashMap<>();
        header.put(Constant.HEADER_APP_KEY, appId);
        header.put(Constant.HEADER_APP_AUTH, "Bearer" + " " + accessToken);

        StreamClosedHttpResponse bodyQuerySpecifyDevice = httpsUtil.doGetWithParasGetStatusLine(urlQuerySpecifyDevice,
                paramQuerySpecifyDevice, header);

        node = JsonUtil.convertJsonStringToObject( bodyQuerySpecifyDevice.getContent(), JsonNode.class );

        return node;
    }

    public static JsonNode getAllDeviceInfos(String appId, String secret) throws Exception {
        // Two-Way Authentication
        HttpsUtil httpsUtil = new HttpsUtil();
        httpsUtil.initSSLConfigForTwoWay();

        // Authentication.get token
        String accessToken = login(httpsUtil, appId, secret);

        //Please make sure that the following parameter values have been modified in the Constant file.
        String urlQueryDeviceGroups = Constant.QUERY_BATCH_DEVICES;

        //please replace the pageSize, when you call this interface.
        Integer pageSize = 100;

        Map<String, String> paramQueryDeviceGroups = new HashMap<>();
        paramQueryDeviceGroups.put("pageSize", pageSize.toString());

        Map<String, String> header = new HashMap<>();
        header.put(Constant.HEADER_APP_KEY, appId);
        header.put(Constant.HEADER_APP_AUTH, "Bearer" + " " + accessToken);

        StreamClosedHttpResponse responseQueryDeviceGroups =
                httpsUtil.doGetWithParasGetStatusLine(urlQueryDeviceGroups, paramQueryDeviceGroups, header);

        JsonNode node = JsonUtil.convertJsonStringToObject(responseQueryDeviceGroups.getContent() ,JsonNode.class);

        node = node.get("devices");

        return node;
    }

    public static String login(HttpsUtil httpsUtil, String appId, String secret) throws Exception {//获取accessToken
        String urlLogin = Constant.APP_AUTH;

        Map<String, String> paramLogin = new HashMap<>();
        paramLogin.put("appId", appId);
        paramLogin.put("secret", secret);

        StreamClosedHttpResponse responseLogin = httpsUtil.doPostFormUrlEncodedGetStatusLine(urlLogin, paramLogin);

        System.out.println("app auth success,return accessToken:");
        System.out.println(responseLogin.getStatusLine());
        System.out.println(responseLogin.getContent());
        System.out.println();

        Map<String, String> data = new HashMap<>();
        data = JsonUtil.jsonString2SimpleObj(responseLogin.getContent(), data.getClass());
        return data.get("accessToken");
    }

    /*public static synchronized void getRefreshToken(HttpsUtil httpsUtil) throws Exception {
        String urlLogin = Constant.APP_AUTH;

        Map<String, String> paramLogin = new HashMap<>();
        paramLogin.put("appId", appId);
        paramLogin.put("secret", secret);

        StreamClosedHttpResponse responseLogin = httpsUtil.doPostFormUrlEncodedGetStatusLine(urlLogin, paramLogin);

        System.out.println("app auth success,return accessToken:");
        System.out.println(responseLogin.getStatusLine());
        System.out.println(responseLogin.getContent());
        System.out.println();

        Map<String, String> data = new HashMap<>();
        data = JsonUtil.jsonString2SimpleObj(responseLogin.getContent(), data.getClass());

        accessToken =  data.get("refreshToken");
    }*/
}