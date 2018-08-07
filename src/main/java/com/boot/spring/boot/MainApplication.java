package com.boot.spring.boot;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

/**
 * Created by BHWL on 2017-08-09.
 */
@SpringBootApplication
@EnableElasticsearchRepositories(basePackages = {"com.myweb.elasticsearch.dao"})
@ComponentScan(basePackages = {"com.myweb"})
public class MainApplication {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(MainApplication.class, args);
    }
}
