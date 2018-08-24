package com.myweb.grpc.demo;

import com.myweb.avro.AvroUtils;
import com.myweb.domain.DepthPriceRaw;
import com.myweb.domain.Price;
import com.myweb.s3.S3Utils;
import com.myweb.vo.Parameter;
import org.apache.avro.file.DataFileReader;
import org.apache.avro.file.DataFileWriter;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Test {

    public static void main(String[] args) {

        S3Utils s3Utils = new S3Utils();
        DepthPriceRaw depthPriceRaw = new DepthPriceRaw();
        depthPriceRaw.setSymbol("111");
        depthPriceRaw.setCounterParty("1111");
        depthPriceRaw.setTimestamp(new Date().getTime());
        Price price = new Price();
        price.setPrice(new BigDecimal(11111));
        price.setQuantity(new BigDecimal(11111));
        List<Price> priceList = new ArrayList<Price>();
        priceList.add(price);
        depthPriceRaw.setAsks(priceList);
        depthPriceRaw.setBids(priceList);

        Parameter parameter = new Parameter();
        parameter.setEndTimestamp(depthPriceRaw.getTimestamp());
        File file = s3Utils.makeTempFile(s3Utils.makePath(parameter), s3Utils.makeDepthPriceFileName(parameter, depthPriceRaw));
        DataFileWriter<DepthPriceRaw> dataFileWriter = (DataFileWriter<DepthPriceRaw>) AvroUtils.getDataFileWriter(DepthPriceRaw.class, file);
        AvroUtils.writeToAvro(dataFileWriter, depthPriceRaw, DepthPriceRaw.class);
        AvroUtils.closeWriter(dataFileWriter);

        DataFileReader<DepthPriceRaw> dataFileReader = (DataFileReader<DepthPriceRaw>) AvroUtils.getDataFileReader(file, DepthPriceRaw.class);
        DepthPriceRaw depthPriceRaw2 = null;
        while (dataFileReader != null && dataFileReader.hasNext()) {
            try {
                depthPriceRaw2 = dataFileReader.next(depthPriceRaw);
                System.out.println(depthPriceRaw2.getTimestamp());
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        AvroUtils.closeDataFileReader(dataFileReader);
    }
}
