package com.zx.demo.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DeviceInfo_saveToDataBase {
    String device_mac_id;
    int device_id;

    public DeviceInfo_saveToDataBase(String device_mac_id, int device_id) {
        this.device_mac_id = device_mac_id;
        this.device_id = device_id;
    }

    public DeviceInfo_saveToDataBase() {
    }
}
