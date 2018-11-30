package com.myweb.service.impl;

import com.myweb.domain.XiuZheng;
import com.myweb.service.OneService;
import com.utils.Result;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;


@Service("OneService")
@SuppressWarnings("All")
@Transactional(readOnly = true)
public class OneServiceImpl implements OneService {

    private static final Logger logger = LogManager.getLogger(OneServiceImpl.class);

    @Autowired
    private JdbcTemplate impalaJdbcTemplate;

    @Override
    public Result query( ) {
        Result result = new Result();
        String sql = "select * from  afc.dp_list_cust limit 100";
        List<Map<String, Object>> maps = impalaJdbcTemplate.queryForList(sql);
        for (Map<String, Object> map : maps) {
            System.out.println(map.toString());
        }
        return result;
    }
}
