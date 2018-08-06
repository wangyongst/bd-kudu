package com.boot.spring.boot;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by BHWL on 2017-08-09.
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.myweb.kafka"})
@ComponentScan(basePackages = {"com.myweb.elasticsearch.dao"})
@ComponentScan(basePackages = {"com.myweb.pojo"})
public class MainApplication {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(MainApplication.class, args);
    }
}
