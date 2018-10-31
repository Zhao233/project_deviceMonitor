package com.zx.demo.model;


import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Date;

@Getter
@Setter
public class Test1 {
    int device_id;//device_info
    String name;//device
    Date time;

    public Test1(int device_id, String name, Date time) {
        this.device_id = device_id;
        this.name = name;
        this.time = time;
    }

    public Test1(){}
}
