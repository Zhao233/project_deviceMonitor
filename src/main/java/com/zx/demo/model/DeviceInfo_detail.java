package com.zx.demo.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;


@Getter
@Setter
public class DeviceInfo_detail {
    long deviceId;

    String device_mac_id;
    String name;
    String attributionOfDevice;

    int organizationId;
    String addressOfDevice;
    int times;
    String signalIntensity;
    int statusOfCharge;
    int statusOfLight;
    String temperature;
    String humidity;

    String imei_id;

    Date updateTime;

    public DeviceInfo_detail(long   deviceId,                  String device_mac_id,        String name,
                             String attributionOfDevice, int    organizationId,  String addressOfDevice,
                             int    times,               String signalIntensity, int    statusOfCharge,
                             int    statusOfLight,       String temperature,     String humidity,
                             Date   updateTime, String imei_id) {

        this.deviceId = deviceId;
        this.device_mac_id = device_mac_id;
        this.name = name;
        this.attributionOfDevice = attributionOfDevice;
        this.organizationId = organizationId;
        this.addressOfDevice = addressOfDevice;
        this.times = times;
        this.signalIntensity = signalIntensity;
        this.statusOfCharge = statusOfCharge;
        this.statusOfLight = statusOfLight;
        this.temperature = temperature;
        this.humidity = humidity;
        this.updateTime = updateTime;
        this.imei_id = imei_id;
    }

    public DeviceInfo_detail(){}
}
