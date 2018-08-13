package com.myweb.elasticsearch;

import com.myweb.elasticsearch.service.OneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class TimerTask {

    @Autowired
    private OneService oneService;
    static int num = 0;


    @Scheduled(cron = "0/20 * * * * ?") // 每20秒执行一次
    public void scheduler() {
        System.out.println(">>>>>>>>>:" + num);
        System.out.println(oneService.toString());
        num++;
    }
}