package com.zx.demo.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.Date;

@Data
@MappedSuperclass
public class DeviceInfo_all implements Serializable {
    public DeviceInfo_all(long deviceId, String device_mac_id, int type, int level, String name, int statusOfLight, int statusOfCharge, double temperature, double humidity, Date updateTime, Date modify_time_search_local, String imei_id) {
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
        this.modify_time_search_local = modify_time_search_local;
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
    private double temperature;
    private double humidity;
    private Date updateTime;
    private Date modify_time_search_local;
}
