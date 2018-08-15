package com.myweb.elasticsearch.service.impl;


import com.myweb.domain.DepthPriceRaw;
import com.myweb.domain.TradeHistoryRaw;
import com.myweb.elasticsearch.dao.DepthPriceRawRepository;
import com.myweb.elasticsearch.dao.TradeHistoryRawRepository;
import com.myweb.elasticsearch.service.OneService;
import com.myweb.vo.Parameter;
import org.apache.avro.file.DataFileReader;
import org.apache.avro.file.DataFileWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Autowired
    private DepthPriceRawRepository depthPriceRawRepository;

    @Autowired
    private TradeHistoryRawRepository tradeHistoryRawRepository;

    @Value("${custom.avro.path.depthpriceraw}")
    private String depthpricerawpath;

    @Value("${custom.avro.path.tradehistoryraw}")
    private String tradehistoryrawpath;

    @Override
    public List<DepthPriceRaw> queryDepthPriceRaw(Parameter parameter) {
        List<DepthPriceRaw> depthPriceRaws = new ArrayList<DepthPriceRaw>();
        if (parameter.getStartTimestamp() == null || parameter.getEndTimestamp() == null) return depthPriceRaws;
        if (parameter.getCounterParty() == null && parameter.getSymbol() == null) {
            depthPriceRaws = depthPriceRawRepository.findByTimestampBetween(parameter.getStartTimestamp().longValue(), parameter.getEndTimestamp().longValue());
        } else if (parameter.getCounterParty() == null && parameter.getSymbol() != null) {
            depthPriceRaws = depthPriceRawRepository.findBySymbolInAndTimestampBetween(parameter.getSymbol(), parameter.getStartTimestamp().longValue(), parameter.getEndTimestamp().longValue());
        } else if (parameter.getCounterParty() != null && parameter.getSymbol() == null) {
            depthPriceRaws = depthPriceRawRepository.findByCounterPartyInAndTimestampBetween(parameter.getCounterParty(), parameter.getStartTimestamp().longValue(), parameter.getEndTimestamp().longValue());
        } else if (parameter.getCounterParty() != null && parameter.getSymbol() != null) {
            depthPriceRaws = depthPriceRawRepository.findByCounterPartyInAndSymbolInAndTimestampBetween(parameter.getCounterParty(), parameter.getSymbol(), parameter.getStartTimestamp().longValue(), parameter.getEndTimestamp().longValue());
        }
        List<DepthPriceRaw> depthPriceRawList = new ArrayList<DepthPriceRaw>();
        depthPriceRawList.addAll(searchDepthPriceRaw(parameter));
        depthPriceRawList.addAll(depthPriceRaws);
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
        if (parameter.getStartTimestamp() == null || parameter.getEndTimestamp() == null) return null;
        List<TradeHistoryRaw> tradeHistoryRaws = new ArrayList<TradeHistoryRaw>();
        if (parameter.getCounterParty() == null && parameter.getSymbol() == null) {
            tradeHistoryRaws = tradeHistoryRawRepository.findByTimestampBetween(parameter.getStartTimestamp().longValue(), parameter.getEndTimestamp().longValue());
        } else if (parameter.getCounterParty() == null && parameter.getSymbol() != null) {
            tradeHistoryRaws = tradeHistoryRawRepository.findBySymbolInAndTimestampBetween(parameter.getSymbol(), parameter.getStartTimestamp().longValue(), parameter.getEndTimestamp().longValue());
        } else if (parameter.getCounterParty() != null && parameter.getSymbol() == null) {
            tradeHistoryRaws = tradeHistoryRawRepository.findByCounterPartyInAndTimestampBetween(parameter.getCounterParty(), parameter.getStartTimestamp().longValue(), parameter.getEndTimestamp().longValue());
        } else if (parameter.getCounterParty() != null && parameter.getSymbol() != null) {
            tradeHistoryRaws = tradeHistoryRawRepository.findByCounterPartyInAndSymbolInAndTimestampBetween(parameter.getCounterParty(), parameter.getSymbol(), parameter.getStartTimestamp().longValue(), parameter.getEndTimestamp().longValue());
        }
        List<TradeHistoryRaw> tradeHistoryRawList = new ArrayList<TradeHistoryRaw>();
        tradeHistoryRawList.addAll(searchTradeHistoryRaw(parameter));
        tradeHistoryRawList.addAll(tradeHistoryRaws);
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
        List<DepthPriceRaw> depthPriceRaws = depthPriceRawRepository.findByTimestampBetween(parameter.getStartTimestamp().longValue(), parameter.getEndTimestamp().longValue());
        //file avro
        if (depthPriceRaws.size() == 0) return true;
        DataFileWriter<DepthPriceRaw> dataFileWriter = (DataFileWriter<DepthPriceRaw>) ServiceUtils.getDataFileWriter(DepthPriceRaw.class, ServiceUtils.makePath(depthpricerawpath, parameter) + File.separator + "dpr-" + parameter.getStartTimestamp() + "-" + parameter.getEndTimestamp());
        depthPriceRaws.forEach(e -> {
            ServiceUtils.writeToAvro(dataFileWriter, e, DepthPriceRaw.class);
        });
        ServiceUtils.closeWriter(dataFileWriter);
        //delete
        depthPriceRawRepository.deleteAllByTimestampBetween(0L, parameter.getEndTimestamp());
        return true;
    }

    @Override
    public boolean transTradeHistoryRaw(Parameter parameter) {
        List<TradeHistoryRaw> tradeHistoryRaws = tradeHistoryRawRepository.findByTimestampBetween(parameter.getStartTimestamp().longValue(), parameter.getEndTimestamp().longValue());
        //file avro
        if (tradeHistoryRaws.size() == 0) return true;
        DataFileWriter<TradeHistoryRaw> dataFileWriter = (DataFileWriter<TradeHistoryRaw>) ServiceUtils.getDataFileWriter(TradeHistoryRaw.class, ServiceUtils.makePath(tradehistoryrawpath, parameter) + File.separator + "thr-" + parameter.getStartTimestamp() + "-" + parameter.getEndTimestamp());
        tradeHistoryRaws.forEach(e -> {
            ServiceUtils.writeToAvro(dataFileWriter, e, DepthPriceRaw.class);
        });
        ServiceUtils.closeWriter(dataFileWriter);
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
                    e1.printStackTrace();
                }
            }
            ServiceUtils.closeDataFileReader(dataFileReader);
        });
        return this.searchDepthPriceRawBySymbol(parameter, this.searchDepthPriceRawByCounterParty(parameter, this.searchDepthPriceRawByTimstamp(parameter, depthPriceRaws)));
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
                    e1.printStackTrace();
                }
            }
            ServiceUtils.closeDataFileReader(dataFileReader);
        });
        return tradeHistoryRaws;
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
            if (parameter.getCounterParty() != null && parameter.getCounterParty().size() > 0 && parameter.getCounterParty().contains(e.getCounterParty())) {
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

    public List<TradeHistoryRaw> searchTradeHistoryRaw(Parameter parameter, List<TradeHistoryRaw> tradeHistoryRaws) {
        List<File> files = ServiceUtils.getFile(tradehistoryrawpath, parameter);
        files.forEach(e -> {

            DataFileReader<TradeHistoryRaw> dataFileReader = (DataFileReader<TradeHistoryRaw>) ServiceUtils.getDataFileReader(e.getAbsolutePath(), TradeHistoryRaw.class);
            TradeHistoryRaw tradeHistoryRaw = null;
            while (dataFileReader.hasNext()) {
                try {
                    tradeHistoryRaw = dataFileReader.next(tradeHistoryRaw);
                    tradeHistoryRaws.add(tradeHistoryRaw);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            ServiceUtils.closeDataFileReader(dataFileReader);
        });
        return tradeHistoryRaws;
    }
}
