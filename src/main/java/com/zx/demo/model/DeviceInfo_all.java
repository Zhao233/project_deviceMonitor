package com.zx.demo.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Setter
@Getter
public class DeviceInfo_all implements Serializable {
    public DeviceInfo_all(long deviceId, String device_mac_id, int type, int level, String name, int statusOfLight, int statusOfCharge, String temperature, String humidity, Date updateTime, String imei_id) {
        this.deviceId = deviceId;
        this.device_mac_id = device_mac_id;
        this.type = type;
        this.level = level;
        this.name = name;
        this.statusOfLight = statusOfLight;
        this.statusOfCharge = statusOfCharge;
        this.temperature = temperature;
        this.humidity = humidity;
        this.updateTime = updateTime;
        this.imei_id = imei_id;
    }

    public DeviceInfo_all(){}

    private long deviceId;
    private String device_mac_id;
    private int type;
    private int level;
    private String name;
    private String imei_id;
    private int statusOfLight;
    private int statusOfCharge;
    private String temperature;
    private String humidity;
    private Date updateTime;
}
