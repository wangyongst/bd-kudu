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
    static int num = 0;

    @Scheduled(cron = "0 0 0/1 * * ?") // 每20秒执行一次
    public void scheduler() {
        Parameter parameter = new Parameter();
        parameter.setStartTimestamp(new Date().getTime() - 1000 * 60 * 60);
        parameter.setEndTimestamp(new Date().getTime());
        oneService.transTradeHistoryRaw(parameter);
        oneService.transDepthPriceRaw(parameter);
    }
}