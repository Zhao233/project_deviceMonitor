package com.zx.demo.domain;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "device")

@Getter
@Setter
public class Device {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    @Column(name = "name", length = 200)
    private String name;

    @Column(name = "type")
    private int type;

    @Column(name = "address_of_device", length = 200)
    private String addressOfDevice;

    @Column(name = "attribution_of_device", length = 200)
    private String attributionOfDevice;

    @Column(name = "organization_id")
    private int organizationId;

    @Column(name = "level", length = 200)
    private int level;

    @Column(name = "mac_id")
    private String mac_id;

    @Column(name = "imei_id")
    private String imei_id;

    @Column(name = "card_number")
    private String cardNumber;

    @Column(name = "update_time")
    private Timestamp update_time;

    @Column(name = "create_time")
    private Timestamp create_time;

    @Column(name = "organization_name")
    private String organization_name;

    @Column(name ="user_id")
    private long user_id;

    public Device(long id, String name, int type, String addressOfDevice, String attributionOfDevice, String organization_name, int organizationId, int level, String mac_id, Timestamp timestamp_update, Timestamp timestamp_create, String imei_id, String cardNumber, long user_id) {
        this.id=id;
        this.name = name;
        this.type = type;
        this.addressOfDevice = addressOfDevice;
        this.attributionOfDevice = attributionOfDevice;
        this.organization_name = organization_name;
        this.organizationId = organizationId;
        this.level = level;
        this.mac_id = mac_id;
        this.create_time = timestamp_create;
        this.update_time = timestamp_update;
        this.imei_id = imei_id;
        this.cardNumber = cardNumber;
        this.user_id = user_id;
    }

    public Device(){}
}
