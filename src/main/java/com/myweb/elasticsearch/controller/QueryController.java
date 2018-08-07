package com.myweb.elasticsearch.controller;

import com.myweb.elasticsearch.dao.DepthPriceRawRepository;
import com.myweb.elasticsearch.dao.TradeHistoryRawRepository;
import com.myweb.vo.Parameter;
import com.myweb.vo.Result;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QueryController {
    @Autowired
    private DepthPriceRawRepository depthPriceRawRepository;

    @Autowired
    private TradeHistoryRawRepository tradeHistoryRawRepository;

    @RequestMapping("/query")
    public Object queryDepthPriceRaw(@RequestBody Parameter parameter) {
        if (parameter.getPage() == null || parameter.getPage().intValue() == 0) parameter.setPage(0);
        if (parameter.getPagesize() == null || parameter.getPagesize().intValue() == 0) parameter.setPagesize(10);
        Pageable pageable = null;
        Sort sort = null;
        if (StringUtils.isNotBlank(parameter.getOrder()) && parameter.getOrder().equals("desc")) {
            sort = new Sort(Sort.Direction.DESC, "timestamp");
        } else if (StringUtils.isBlank(parameter.getOrder()) || parameter.getOrder().equals("asc")) {
            sort = new Sort(Sort.Direction.ASC, "timestamp");
        }
        pageable = new PageRequest(parameter.getPage().intValue(), parameter.getPagesize().intValue(), sort);
        if (StringUtils.isBlank(parameter.getTableName())) {
            Result result = new Result();
            result.setStatus(20001);
            result.setMessage("The parameter tableName can not be empty");
            return result;
        } else {
            if (parameter.getTableName().equals("depth-price-raw")) {
                if (parameter.getCounterparty().size() == 0 && parameter.getSymbol().size() == 0 && parameter.getStartTimestamp().intValue() == 0 && parameter.getEndTimestamp().intValue() == 0) {
                    return depthPriceRawRepository.findAll(pageable);
                } else if (parameter.getCounterparty().size() == 0 && parameter.getSymbol().size() > 0) {

                }
            } else if (parameter.getTableName().equals("trade-history-raw")) {
                return tradeHistoryRawRepository.findAll(pageable);
            } else {
                Result result = new Result();
                result.setStatus(20002);
                result.setMessage("The parameter tableName do not exist");
                return result;
            }
        }
        return null;
    }
}