package com.zx.demo.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "temp")
@Getter
@Setter
public class Temp {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "temperature")
    private String temperature;

    @Column(name = "update_time")
    private String update_time;

    public Temp(String name, String temperature, String update_time) {
        this.name = name;
        this.temperature = temperature;
        this.update_time = update_time;
    }
}
