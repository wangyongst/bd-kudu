package com.myweb.elasticsearch.service.impl;


import com.myweb.domain.DepthPriceRaw;
import com.myweb.domain.TradeHistoryRaw;
import com.myweb.elasticsearch.dao.DepthPriceRawRepository;
import com.myweb.elasticsearch.dao.TradeHistoryRawRepository;
import com.myweb.elasticsearch.service.OneService;
import com.myweb.vo.Parameter;
import org.apache.avro.file.DataFileReader;
import org.apache.avro.file.DataFileWriter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service("OneService")
@SuppressWarnings("All")
public class OneServiceImpl implements OneService {

    @Autowired
    private DepthPriceRawRepository depthPriceRawRepository;

    @Autowired
    private TradeHistoryRawRepository tradeHistoryRawRepository;

    @Override
    public List<DepthPriceRaw> queryDepthPriceRaw(Parameter parameter) {
        if (parameter.getStartTimestamp() == null || parameter.getEndTimestamp() == null) return null;
        if (StringUtils.isNotBlank(parameter.getOrder()) && parameter.getOrder().equals("asc")) {
            if (parameter.getCounterParty() == null && parameter.getSymbol() == null) {
                return depthPriceRawRepository.findByTimestampBetweenOrderByTimestampAsc(parameter.getStartTimestamp().longValue(), parameter.getEndTimestamp().longValue());
            } else if (parameter.getCounterParty() == null && parameter.getSymbol() != null) {
                return depthPriceRawRepository.findBySymbolInAndTimestampBetweenOrderByTimestampAsc(parameter.getSymbol(), parameter.getStartTimestamp().longValue(), parameter.getEndTimestamp().longValue());
            } else if (parameter.getCounterParty() != null && parameter.getSymbol() == null) {
                return depthPriceRawRepository.findByCounterPartyInAndTimestampBetweenOrderByTimestampAsc(parameter.getCounterParty(), parameter.getStartTimestamp().longValue(), parameter.getEndTimestamp().longValue());
            } else if (parameter.getCounterParty() != null && parameter.getSymbol() != null) {
                return depthPriceRawRepository.findByCounterPartyInAndSymbolInAndTimestampBetweenOrderByTimestampAsc(parameter.getCounterParty(), parameter.getSymbol(), parameter.getStartTimestamp().longValue(), parameter.getEndTimestamp().longValue());
            }
        } else if (StringUtils.isNotBlank(parameter.getOrder()) && parameter.getOrder().equals("desc")) {
            if (parameter.getCounterParty() == null && parameter.getSymbol() == null) {
                return depthPriceRawRepository.findByTimestampBetweenOrderByTimestampDesc(parameter.getStartTimestamp().longValue(), parameter.getEndTimestamp().longValue());
            } else if (parameter.getCounterParty() == null && parameter.getSymbol() != null) {
                return depthPriceRawRepository.findBySymbolInAndTimestampBetweenOrderByTimestampDesc(parameter.getSymbol(), parameter.getStartTimestamp().longValue(), parameter.getEndTimestamp().longValue());
            } else if (parameter.getCounterParty() != null && parameter.getSymbol() == null) {
                return depthPriceRawRepository.findByCounterPartyInAndTimestampBetweenOrderByTimestampDesc(parameter.getCounterParty(), parameter.getStartTimestamp().longValue(), parameter.getEndTimestamp().longValue());
            } else if (parameter.getCounterParty() != null && parameter.getSymbol() != null) {
                return depthPriceRawRepository.findByCounterPartyInAndSymbolInAndTimestampBetweenOrderByTimestampDesc(parameter.getCounterParty(), parameter.getSymbol(), parameter.getStartTimestamp().longValue(), parameter.getEndTimestamp().longValue());
            }
        }
        return null;
    }

    @Override
    public List<TradeHistoryRaw> queryTradeHistoryRaw(Parameter parameter) {
        if (parameter.getStartTimestamp() == null || parameter.getEndTimestamp() == null) return null;
        if (StringUtils.isNotBlank(parameter.getOrder()) && parameter.getOrder().equals("asc")) {
            if (parameter.getCounterParty() == null && parameter.getSymbol() == null) {
                return tradeHistoryRawRepository.findByTimestampBetweenOrderByTimestampAsc(parameter.getStartTimestamp().longValue(), parameter.getEndTimestamp().longValue());
            } else if (parameter.getCounterParty() == null && parameter.getSymbol() != null) {
                return tradeHistoryRawRepository.findBySymbolInAndTimestampBetweenOrderByTimestampAsc(parameter.getSymbol(), parameter.getStartTimestamp().longValue(), parameter.getEndTimestamp().longValue());
            } else if (parameter.getCounterParty() != null && parameter.getSymbol() == null) {
                return tradeHistoryRawRepository.findByCounterPartyInAndTimestampBetweenOrderByTimestampAsc(parameter.getCounterParty(), parameter.getStartTimestamp().longValue(), parameter.getEndTimestamp().longValue());
            } else if (parameter.getCounterParty() != null && parameter.getSymbol() != null) {
                return tradeHistoryRawRepository.findByCounterPartyInAndSymbolInAndTimestampBetweenOrderByTimestampAsc(parameter.getCounterParty(), parameter.getSymbol(), parameter.getStartTimestamp().longValue(), parameter.getEndTimestamp().longValue());
            }
        } else if (StringUtils.isNotBlank(parameter.getOrder()) && parameter.getOrder().equals("desc")) {
            if (parameter.getCounterParty() == null && parameter.getSymbol() == null) {
                return tradeHistoryRawRepository.findByTimestampBetweenOrderByTimestampDesc(parameter.getStartTimestamp().longValue(), parameter.getEndTimestamp().longValue());
            } else if (parameter.getCounterParty() == null && parameter.getSymbol() != null) {
                return tradeHistoryRawRepository.findBySymbolInAndTimestampBetweenOrderByTimestampDesc(parameter.getSymbol(), parameter.getStartTimestamp().longValue(), parameter.getEndTimestamp().longValue());
            } else if (parameter.getCounterParty() != null && parameter.getSymbol() == null) {
                return tradeHistoryRawRepository.findByCounterPartyInAndTimestampBetweenOrderByTimestampDesc(parameter.getCounterParty(), parameter.getStartTimestamp().longValue(), parameter.getEndTimestamp().longValue());
            } else if (parameter.getCounterParty() != null && parameter.getSymbol() != null) {
                return tradeHistoryRawRepository.findByCounterPartyInAndSymbolInAndTimestampBetweenOrderByTimestampDesc(parameter.getCounterParty(), parameter.getSymbol(), parameter.getStartTimestamp().longValue(), parameter.getEndTimestamp().longValue());
            }
        }
        return null;
    }

    @Override
    public boolean transDepthPriceRaw(Parameter parameter) {
        List<DepthPriceRaw> depthPriceRaws = queryDepthPriceRaw(parameter);
        //file avro
        DataFileWriter<DepthPriceRaw> dataFileWriter = (DataFileWriter<DepthPriceRaw>) ServiceUtils.getDataFileWriter(DepthPriceRaw.class, "dpr" + parameter.getStartTimestamp() + "-" + parameter.getEndTimestamp() + ".avro");
        depthPriceRaws.forEach(e -> {
            ServiceUtils.writeToAvro(dataFileWriter, e, DepthPriceRaw.class);
        });
        ServiceUtils.closeWriter(dataFileWriter);
        //delete
        depthPriceRawRepository.deleteAll(depthPriceRaws);
        return true;
    }

    @Override
    public boolean transTradeHistoryRaw(Parameter parameter) {
        List<TradeHistoryRaw> tradeHistoryRaws = queryTradeHistoryRaw(parameter);
        //file avro
        DataFileWriter<TradeHistoryRaw> dataFileWriter = (DataFileWriter<TradeHistoryRaw>) ServiceUtils.getDataFileWriter(TradeHistoryRaw.class, "thr" + parameter.getStartTimestamp() + "-" + parameter.getEndTimestamp() + ".avro");
        tradeHistoryRaws.forEach(e -> {
            ServiceUtils.writeToAvro(dataFileWriter, e, DepthPriceRaw.class);
        });
        ServiceUtils.closeWriter(dataFileWriter);
        //delete
        tradeHistoryRawRepository.deleteAll(tradeHistoryRaws);
        return true;
    }

    @Override
    public boolean restoreDepthPriceRaw(Parameter parameter) {
        List<DepthPriceRaw> depthPriceRaws = new ArrayList<DepthPriceRaw>();
        //file avro
        DataFileReader<DepthPriceRaw> dataFileReader = (DataFileReader<DepthPriceRaw>) ServiceUtils.getDataFileReader("dpr" + parameter.getStartTimestamp() + "-" + parameter.getEndTimestamp() + ".avro", DepthPriceRaw.class);
        DepthPriceRaw depthPriceRaw = null;
        while (dataFileReader.hasNext()) {
            try {
                depthPriceRaw = dataFileReader.next(depthPriceRaw);
                depthPriceRaws.add(depthPriceRaw);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        ServiceUtils.closeDataFileReader(dataFileReader);
        //restore
        depthPriceRawRepository.saveAll(depthPriceRaws);
        return true;
    }

    @Override
    public boolean restoreTradeHistoryRaw(Parameter parameter) {
        List<TradeHistoryRaw> tradeHistoryRaws = new ArrayList<TradeHistoryRaw>();
        //file avro
        DataFileReader<TradeHistoryRaw> dataFileReader = (DataFileReader<TradeHistoryRaw>) ServiceUtils.getDataFileReader("thr" + parameter.getStartTimestamp() + "-" + parameter.getEndTimestamp() + ".avro", TradeHistoryRaw.class);
        TradeHistoryRaw tradeHistoryRaw = null;
        while (dataFileReader.hasNext()) {
            try {
                tradeHistoryRaw = dataFileReader.next(tradeHistoryRaw);
                tradeHistoryRaws.add(tradeHistoryRaw);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        ServiceUtils.closeDataFileReader(dataFileReader);
        //restore
        tradeHistoryRawRepository.saveAll(tradeHistoryRaws);
        return true;
    }


}
