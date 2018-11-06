package com.myweb.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myweb.domain.XiuZheng;
import com.myweb.service.OneService;
import com.utils.Result;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.StringWriter;


@Service("OneService")
@SuppressWarnings("All")
@Transactional(readOnly = true)
public class OneServiceImpl implements OneService {

    private static final Logger logger = LogManager.getLogger(OneServiceImpl.class);

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Result sendMessage(XiuZheng xiuZheng) {
        Result result = new Result();
        StringWriter str = new StringWriter();
        try {
            objectMapper.writeValue(str, xiuZheng);
        } catch (IOException e) {
            e.printStackTrace();
        }
        result.setMessage(str.toString());
        return result;
    }

}
