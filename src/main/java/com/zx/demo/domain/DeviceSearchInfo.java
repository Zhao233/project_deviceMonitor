package com.zx.demo.domain;


import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "device_search_info")
@Data
public class DeviceSearchInfo {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long ID;

    @Column(name = "appId")
    private String AppID;

    @Column(name = "secret")
    private String Secret;
}
