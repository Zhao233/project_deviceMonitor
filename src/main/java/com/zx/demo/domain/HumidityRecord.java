package com.zx.demo.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "humidityrecord")
@Getter
@Setter
public class HumidityRecord {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @Column(name = "date")
    private String date;

    @Column(name = "humidity")
    private String humidity;
}
