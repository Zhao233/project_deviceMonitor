package com.zx.demo.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "organization")
@Getter
@Setter
public class Organization {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    @Column(name = "name", length = 200)
    private String name;
}
