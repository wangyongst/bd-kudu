package com.boot.spring.boot;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * Created by BHWL on 2017-08-09.
 */
@SpringBootApplication(scanBasePackages = {"com.myweb"})
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
public class MainApplication {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(MainApplication.class, args);
    }
}
