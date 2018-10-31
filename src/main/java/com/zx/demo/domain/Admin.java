package com.zx.demo.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.persistence.*;

@Entity
@Table(name = "admin")
@Component
@Getter
@Setter
public class Admin{
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "PASSWORD")
    private String password;

    public Admin concerter() {
        Admin admin = new Admin();

        admin.setId(this.id);
        admin.setName(this.name.trim().toLowerCase());
        admin.setPassword(this.password.trim());

        return admin;
    }
}
