package com.zx.demo.domain;


import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "device_info_new")
@Data
public class NewDeviceInfo {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name = "device_mac_id")
    private String device_mac_id;

    @Column(name = "device_id")
    private Long device_id;

    @Column(name = "humidity")
    private double humidity;

    @Column(name = "status")
    private String status;

    @Column(name = "signal_intensity")
    private String signalIntensity;

    @Column(name = "status_of_charge")
    private int statusOfCharge;

    @Column(name = "status_of_light")
    private int statusOfLight;

    @Column(name = "temperature")
    private double temperature;

    @Column(name = "times")
    private int times;

    @Column(name = "imei_id")
    private String imei_id;

    int totalNumber;

    /**
     * 电信服务器查询的时间
     * */
    @Column(name = "update_time")
    private Timestamp updateTime;

    /**
     * 本机查询的时间
     * */
    @Column(name = "modify_time_search_local")
    private Timestamp modify_time_search_local;

    public NewDeviceInfo(String device_mac_id, long device_id, double humidity, String status, String signalIntensity, int statusOfCharge, int statusOfLight, double temperature, int times, String imei_id, Timestamp updateTime, Timestamp modify_time_search_local) {
        this.id = Long.valueOf(0);
        this.device_mac_id = device_mac_id;
        this.device_id = device_id;
        this.humidity = humidity;
        this.status = status;
        this.signalIntensity = signalIntensity;
        this.statusOfCharge = statusOfCharge;
        this.statusOfLight = statusOfLight;
        this.temperature = temperature;
        this.times = times;
        this.imei_id = imei_id;
        this.updateTime = updateTime;
        this.modify_time_search_local = modify_time_search_local;
    }

    public NewDeviceInfo(){}
}
