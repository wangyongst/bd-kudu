package com.myweb.elasticsearch.service.impl;


import com.myweb.domain.DepthPriceRaw;
import com.myweb.domain.TradeHistoryRaw;
import com.myweb.elasticsearch.dao.DepthPriceRawRepository;
import com.myweb.elasticsearch.dao.TradeHistoryRawRepository;
import com.myweb.elasticsearch.service.OneService;
import com.myweb.vo.Parameter;
import org.apache.avro.file.DataFileReader;
import org.apache.avro.file.DataFileWriter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service("OneService")
@SuppressWarnings("All")
public class OneServiceImpl implements OneService {

    private static final Logger logger = LogManager.getLogger(OneServiceImpl.class);

    @Autowired
    private DepthPriceRawRepository depthPriceRawRepository;

    @Autowired
    private TradeHistoryRawRepository tradeHistoryRawRepository;

    @Value("${custom.avro.path.depthpriceraw}")
    private String depthpricerawpath;

    @Value("${custom.avro.path.tradehistoryraw}")
    private String tradehistoryrawpath;

    private Pageable pageable = PageRequest.of(0, 10000);

    @Override
    public List<DepthPriceRaw> queryDepthPriceRaw(Parameter parameter) {
        List<DepthPriceRaw> depthPriceRawList = new ArrayList<DepthPriceRaw>();
        if (parameter.getStartTimestamp() == null || parameter.getEndTimestamp() == null) return depthPriceRawList;
        depthPriceRawList.addAll(searchDepthPriceRaw(parameter));
        depthPriceRawList.addAll(depthPriceRawRepository.search(makeQuery(parameter), pageable).getContent());
        if (parameter.getOrder() != null && parameter.getOrder().equals("asc")) {
            Collections.sort(depthPriceRawList, new Comparator<DepthPriceRaw>() {
                @Override
                public int compare(DepthPriceRaw d1, DepthPriceRaw d2) {
                    return d1.getTimestamp().compareTo(d2.getTimestamp());
                }
            });
        } else if (parameter.getOrder() != null && parameter.getOrder().equals("desc")) {
            Collections.sort(depthPriceRawList, new Comparator<DepthPriceRaw>() {
                @Override
                public int compare(DepthPriceRaw d1, DepthPriceRaw d2) {
                    return d2.getTimestamp().compareTo(d1.getTimestamp());
                }
            });
        }
        return depthPriceRawList;
    }

    @Override
    public List<TradeHistoryRaw> queryTradeHistoryRaw(Parameter parameter) {
        List<TradeHistoryRaw> tradeHistoryRawList = new ArrayList<TradeHistoryRaw>();
        if (parameter.getStartTimestamp() == null || parameter.getEndTimestamp() == null) return tradeHistoryRawList;
        tradeHistoryRawList.addAll(searchTradeHistoryRaw(parameter));
        tradeHistoryRawList.addAll(tradeHistoryRawRepository.search(makeQuery(parameter), pageable).getContent());
        if (parameter.getOrder() != null && parameter.getOrder().equals("asc")) {
            Collections.sort(tradeHistoryRawList, new Comparator<TradeHistoryRaw>() {
                @Override
                public int compare(TradeHistoryRaw d1, TradeHistoryRaw d2) {
                    return d1.getTimestamp().compareTo(d2.getTimestamp());
                }
            });
        } else if (parameter.getOrder() != null && parameter.getOrder().equals("desc")) {
            Collections.sort(tradeHistoryRawList, new Comparator<TradeHistoryRaw>() {
                @Override
                public int compare(TradeHistoryRaw d1, TradeHistoryRaw d2) {
                    return d2.getTimestamp().compareTo(d1.getTimestamp());
                }
            });
        }
        return tradeHistoryRawList;
    }

    @Override
    public boolean transDepthPriceRaw(Parameter parameter) {
        List<DepthPriceRaw> depthPriceRaws = depthPriceRawRepository.findAllByTimestampBetweenOrderByTimestampAsc(parameter.getStartTimestamp().longValue(), parameter.getEndTimestamp().longValue(), pageable);
        //file avro
        if (depthPriceRaws.size() == 0) return true;
        depthPriceRaws.forEach(e -> {
            DataFileWriter<DepthPriceRaw> dataFileWriter = (DataFileWriter<DepthPriceRaw>) ServiceUtils.getDataFileWriter(DepthPriceRaw.class, ServiceUtils.makePath(depthpricerawpath, parameter) + File.separator + e.getCounterParty() + "." + e.getSymbol() + "." + parameter.getStartTimestamp() + "." + parameter.getEndTimestamp() + ".avro");
            ServiceUtils.writeToAvro(dataFileWriter, e, DepthPriceRaw.class);
            ServiceUtils.closeWriter(dataFileWriter);
        });
        //delete
        depthPriceRawRepository.deleteAllByTimestampBetween(0L, parameter.getEndTimestamp());
        return true;
    }

    @Override
    public boolean transTradeHistoryRaw(Parameter parameter) {
        List<TradeHistoryRaw> tradeHistoryRaws = tradeHistoryRawRepository.findAllByTimestampBetweenOrderByTimestampAsc(parameter.getStartTimestamp().longValue(), parameter.getEndTimestamp().longValue(), pageable);
        //file avro
        if (tradeHistoryRaws.size() == 0) return true;
        tradeHistoryRaws.forEach(e -> {
            DataFileWriter<TradeHistoryRaw> dataFileWriter = (DataFileWriter<TradeHistoryRaw>) ServiceUtils.getDataFileWriter(TradeHistoryRaw.class, ServiceUtils.makePath(tradehistoryrawpath, parameter) + File.separator + e.getCounterParty() + "." + e.getSymbol() + "." + parameter.getStartTimestamp() + "." + parameter.getEndTimestamp() + ".avro");
            ServiceUtils.writeToAvro(dataFileWriter, e, TradeHistoryRaw.class);
            ServiceUtils.closeWriter(dataFileWriter);
        });

        //delete
        tradeHistoryRawRepository.deleteAllByTimestampBetween(0L, parameter.getEndTimestamp());
        return true;
    }

    @Override
    public List<DepthPriceRaw> searchDepthPriceRaw(Parameter parameter) {
        List<DepthPriceRaw> depthPriceRaws = new ArrayList<DepthPriceRaw>();
        List<File> files = ServiceUtils.getFile(depthpricerawpath, parameter);
        files.forEach(e -> {
            DataFileReader<DepthPriceRaw> dataFileReader = (DataFileReader<DepthPriceRaw>) ServiceUtils.getDataFileReader(e.getAbsolutePath(), DepthPriceRaw.class);
            DepthPriceRaw depthPriceRaw = null;
            while (dataFileReader != null && dataFileReader.hasNext()) {
                try {
                    depthPriceRaw = dataFileReader.next(depthPriceRaw);
                    depthPriceRaws.add(depthPriceRaw);
                } catch (IOException e1) {
                    logger.error("SearchDepthPriceRaw Error");
                }
            }
            ServiceUtils.closeDataFileReader(dataFileReader);
        });
        return this.searchDepthPriceRawBySymbol(parameter, this.searchDepthPriceRawByCounterParty(parameter, this.searchDepthPriceRawByTimstamp(parameter, depthPriceRaws)));
    }

    public List<DepthPriceRaw> searchDepthPriceRawByTimstamp(Parameter parameter, List<DepthPriceRaw> depthPriceRaws) {
        List<DepthPriceRaw> depthPriceRawList = new ArrayList<>();
        depthPriceRaws.forEach(e -> {
            if (parameter.getStartTimestamp() <= e.getTimestamp() && parameter.getEndTimestamp() >= e.getTimestamp()) {
                depthPriceRawList.add(e);
            }
        });
        return depthPriceRawList;
    }

    public List<DepthPriceRaw> searchDepthPriceRawByCounterParty(Parameter parameter, List<DepthPriceRaw> depthPriceRaws) {
        if (parameter.getCounterParty() == null || parameter.getCounterParty().size() == 0) return depthPriceRaws;
        List<DepthPriceRaw> depthPriceRawList = new ArrayList<>();
        depthPriceRaws.forEach(e -> {
            if (parameter.getCounterParty().contains(e.getCounterParty())) {
                depthPriceRawList.add(e);
            }
        });
        return depthPriceRawList;
    }

    public List<DepthPriceRaw> searchDepthPriceRawBySymbol(Parameter parameter, List<DepthPriceRaw> depthPriceRaws) {
        if (parameter.getSymbol() == null || parameter.getSymbol().size() == 0) return depthPriceRaws;
        List<DepthPriceRaw> depthPriceRawList = new ArrayList<>();
        depthPriceRaws.forEach(e -> {
            if (parameter.getSymbol().contains(e.getSymbol())) {
                depthPriceRawList.add(e);
            }
        });
        return depthPriceRawList;
    }


    @Override
    public List<TradeHistoryRaw> searchTradeHistoryRaw(Parameter parameter) {
        List<TradeHistoryRaw> tradeHistoryRaws = new ArrayList<TradeHistoryRaw>();
        List<File> files = ServiceUtils.getFile(tradehistoryrawpath, parameter);
        files.forEach(e -> {
            DataFileReader<TradeHistoryRaw> dataFileReader = (DataFileReader<TradeHistoryRaw>) ServiceUtils.getDataFileReader(e.getAbsolutePath(), TradeHistoryRaw.class);
            TradeHistoryRaw tradeHistoryRaw = null;
            while (dataFileReader.hasNext()) {
                try {
                    tradeHistoryRaw = dataFileReader.next(tradeHistoryRaw);
                    tradeHistoryRaws.add(tradeHistoryRaw);
                } catch (IOException e1) {
                    logger.error("SearchTradeHistoryRaw Error");
                }
            }
            ServiceUtils.closeDataFileReader(dataFileReader);
        });
        return this.searchTradeHistoryRawBySymbol(parameter, this.searchTradeHistoryRawByCounterParty(parameter, this.searchTradeHistoryRawByTimstamp(parameter, tradeHistoryRaws)));
    }

    public List<TradeHistoryRaw> searchTradeHistoryRawByTimstamp(Parameter parameter, List<TradeHistoryRaw> depthPriceRaws) {
        List<TradeHistoryRaw> tradeHistoryRawList = new ArrayList<>();
        depthPriceRaws.forEach(e -> {
            if (parameter.getStartTimestamp() <= e.getTimestamp() && parameter.getEndTimestamp() >= e.getTimestamp()) {
                tradeHistoryRawList.add(e);
            }
        });
        return tradeHistoryRawList;
    }


    public List<TradeHistoryRaw> searchTradeHistoryRawByCounterParty(Parameter parameter, List<TradeHistoryRaw> tradeHistoryRaws) {
        if (parameter.getCounterParty() == null || parameter.getCounterParty().size() == 0) return tradeHistoryRaws;
        List<TradeHistoryRaw> tradeHistoryRawList = new ArrayList<>();
        tradeHistoryRaws.forEach(e -> {
            if (parameter.getCounterParty().contains(e.getCounterParty())) {
                tradeHistoryRawList.add(e);
            }
        });
        return tradeHistoryRawList;
    }

    public List<TradeHistoryRaw> searchTradeHistoryRawBySymbol(Parameter parameter, List<TradeHistoryRaw> tradeHistoryRaws) {
        if (parameter.getSymbol() == null || parameter.getSymbol().size() == 0) return tradeHistoryRaws;
        List<TradeHistoryRaw> tradeHistoryRawList = new ArrayList<>();
        tradeHistoryRaws.forEach(e -> {
            if (parameter.getSymbol().contains(e.getSymbol())) {
                tradeHistoryRawList.add(e);
            }
        });
        return tradeHistoryRawList;
    }

    public BoolQueryBuilder makeQuery(Parameter parameter) {
        if (parameter.getStartTimestamp() == null || parameter.getEndTimestamp() == null) return null;
        BoolQueryBuilder queryBuilders = QueryBuilders.boolQuery();
        queryBuilders = queryBuilders.must(QueryBuilders.rangeQuery("timestamp").from(parameter.getStartTimestamp()).to(parameter.getEndTimestamp()));
        if(parameter.getCounterParty() != null && parameter.getCounterParty().size()>0) queryBuilders.must(QueryBuilders.boolQuery().should(QueryBuilders.termsQuery("counterParty",parameter.getCounterParty())));
        if(parameter.getSymbol() != null && parameter.getSymbol().size()>0) queryBuilders.must(QueryBuilders.boolQuery().should(QueryBuilders.termsQuery("symbol",parameter.getSymbol())));
        return queryBuilders;
    }
}
