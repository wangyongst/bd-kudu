package com.boot.spring.boot;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

/**
 * Created by BHWL on 2017-08-09.
 */
@SpringBootApplication(scanBasePackages = {"com.myweb"})
@EnableElasticsearchRepositories(basePackages = {"com.myweb.elasticsearch.dao"})
public class MainApplication {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(MainApplication.class, args);
    }
}
