package com.zx.demo.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "temperature")
@Getter
@Setter
public class TemperatureRecord {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @Column(name = "date")
    private String date;

    @Column(name = "temperature")
    private String temperature;

}
