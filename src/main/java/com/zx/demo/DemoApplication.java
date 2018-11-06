package com.zx.demo;

import com.huawei.iotplatform.client.NorthApiException;
import com.zx.demo.util.DeviceRemoteSearchUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

//@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class, SecurityAutoConfiguration.class})
@SpringBootApplication
@EnableJpaRepositories("com.zx.demo.repository")
@ComponentScan("com.zx.demo.controller")
@ComponentScan("com.zx.demo.configuration")
@ComponentScan("com.zx.demo.service")
@ComponentScan("com.zx.demo.model")
@ComponentScan("com.zx.demo.service.implement")

@EnableScheduling
public class DemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);

        try {
            DeviceRemoteSearchUtil.initRemoteServer();
        } catch (NorthApiException e) {
            e.printStackTrace();
        }
    }
}
