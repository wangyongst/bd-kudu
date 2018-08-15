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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
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

    @Value("${custom.avro.path.depthpriceraw}")
    private String depthpricerawpath;

    @Value("${custom.avro.path.tradehistoryraw}")
    private String tradehistoryrawpath;

    @Override
    public List<DepthPriceRaw> queryDepthPriceRaw(Parameter parameter) {
        if (parameter.getStartTimestamp() == null || parameter.getEndTimestamp() == null) return null;
        this.restoreDepthPriceRaw(parameter);
        if (StringUtils.isBlank(parameter.getOrder()) || (StringUtils.isNotBlank(parameter.getOrder()) && parameter.getOrder().equals("asc"))) {
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
        this.restoreTradeHistoryRaw(parameter);
        if (StringUtils.isBlank(parameter.getOrder()) || (StringUtils.isNotBlank(parameter.getOrder()) && parameter.getOrder().equals("asc"))) {
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
        List<DepthPriceRaw> depthPriceRaws = depthPriceRawRepository.findByTimestampBetweenOrderByTimestampAsc(parameter.getStartTimestamp().longValue(), parameter.getEndTimestamp().longValue());
        //file avro
        if (depthPriceRaws.size() == 0) return true;
        DataFileWriter<DepthPriceRaw> dataFileWriter = (DataFileWriter<DepthPriceRaw>) ServiceUtils.getDataFileWriter(DepthPriceRaw.class, ServiceUtils.makePath(depthpricerawpath, parameter) + File.separator + "dpr-" + parameter.getStartTimestamp() + "-" + parameter.getEndTimestamp());
        depthPriceRaws.forEach(e -> {
            ServiceUtils.writeToAvro(dataFileWriter, e, DepthPriceRaw.class);
        });
        ServiceUtils.closeWriter(dataFileWriter);
        //delete
        Parameter parameter2 = new Parameter();
        parameter2.setStartTimestamp(0L);
        parameter2.setEndTimestamp(parameter.getEndTimestamp());
        List<DepthPriceRaw> depthPriceRaws2 = this.queryDepthPriceRaw(parameter2);
        depthPriceRawRepository.deleteAll(depthPriceRaws2);
        return true;
    }

    @Override
    public boolean transTradeHistoryRaw(Parameter parameter) {
        List<TradeHistoryRaw> tradeHistoryRaws = tradeHistoryRawRepository.findByTimestampBetweenOrderByTimestampAsc(parameter.getStartTimestamp().longValue(), parameter.getEndTimestamp().longValue());
        //file avro
        if (tradeHistoryRaws.size() == 0) return true;
        DataFileWriter<TradeHistoryRaw> dataFileWriter = (DataFileWriter<TradeHistoryRaw>) ServiceUtils.getDataFileWriter(TradeHistoryRaw.class, ServiceUtils.makePath(tradehistoryrawpath, parameter) + File.separator + "thr-" + parameter.getStartTimestamp() + "-" + parameter.getEndTimestamp());
        tradeHistoryRaws.forEach(e -> {
            ServiceUtils.writeToAvro(dataFileWriter, e, DepthPriceRaw.class);
        });
        ServiceUtils.closeWriter(dataFileWriter);
        //delete
        Parameter parameter2 = new Parameter();
        parameter2.setStartTimestamp(0L);
        parameter2.setEndTimestamp(parameter.getEndTimestamp());
        depthPriceRawRepository.deleteAll(this.queryDepthPriceRaw(parameter2));
        return true;
    }

    @Override
    public boolean restoreDepthPriceRaw(Parameter parameter) {
        List<DepthPriceRaw> depthPriceRaws = new ArrayList<DepthPriceRaw>();
        List<File> files = ServiceUtils.getFile(depthpricerawpath, parameter);
        files.forEach(e -> {
            DataFileReader<DepthPriceRaw> dataFileReader = (DataFileReader<DepthPriceRaw>) ServiceUtils.getDataFileReader(e.getAbsolutePath(), DepthPriceRaw.class);
            DepthPriceRaw depthPriceRaw = new DepthPriceRaw();
            while (dataFileReader.hasNext()) {
                try {
                    depthPriceRaw = dataFileReader.next(depthPriceRaw);
                    depthPriceRaws.add(depthPriceRaw);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            ServiceUtils.closeDataFileReader(dataFileReader);
            depthPriceRawRepository.saveAll(depthPriceRaws);
        });
        return true;
    }

    @Override
    public boolean restoreTradeHistoryRaw(Parameter parameter) {
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
            tradeHistoryRawRepository.saveAll(tradeHistoryRaws);
        });
        return true;
    }


    public static void main(String[] args) {
        File file = new File("D:\\cvro\\17757\\dpr-1534284000000-1534287600000");
        DataFileReader<DepthPriceRaw> dataFileReader = (DataFileReader<DepthPriceRaw>) ServiceUtils.getDataFileReader(file.getAbsolutePath(), DepthPriceRaw.class);
        DepthPriceRaw depthPriceRaw = new DepthPriceRaw();
        while (dataFileReader.hasNext()) {
            try {
                depthPriceRaw = dataFileReader.next(depthPriceRaw);
                System.out.println(depthPriceRaw.toString());
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        ServiceUtils.closeDataFileReader(dataFileReader);
    }
}
