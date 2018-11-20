package com.zx.demo.domain;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "config")

@Setter
@Getter
public class Config {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    @Column(name = "temperature_upper_limit")
    private String temperature_upper_limit;

    @Column(name = "temperature_lower_limit")
    private String temperature_lower_limit;

    @Column(name = "humidity_upper_limit")
    private String humidity_upper_limit;

    @Column(name = "humidity_lower_limit")
    private String humidity_lower_limit;

    @Column(name = "device_id")
    private long device_id;
}
