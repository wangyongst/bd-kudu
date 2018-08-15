package com.myweb.elasticsearch;

import com.myweb.elasticsearch.service.OneService;
import com.myweb.vo.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;

@Configuration
@EnableScheduling
public class TimerTask {

    @Autowired
    private OneService oneService;

//    @Scheduled(cron = "0 0 0/1 * * ?") // 每1小时秒执行一次
    @Scheduled(cron = "0/30 * * * * ?") // 每1小时秒执行一次
    public void scheduler() {
        Parameter parameter = new Parameter();
        parameter.setStartTimestamp(new Date().getTime() - 1000 * 60 * 60 * 2);
        parameter.setEndTimestamp(new Date().getTime() - 1000 * 60 * 60);
        while (!oneService.transTradeHistoryRaw(parameter)) ;
        while (!oneService.transDepthPriceRaw(parameter)) ;
    }
}