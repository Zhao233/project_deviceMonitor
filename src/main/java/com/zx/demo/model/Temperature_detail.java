package com.zx.demo.model;


import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class Temperature_detail {
    String temperature;
    Date date;

    public Temperature_detail(String temperature, Date date){
        this.temperature = temperature;
        this.date = date;
    }
}
