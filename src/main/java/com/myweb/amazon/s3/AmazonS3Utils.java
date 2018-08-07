package com.myweb.amazon.s3;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.myweb.pojo.DepthPriceRaw;
import com.myweb.pojo.TradeHistoryRaw;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AmazonS3Utils {
    @Value("${amazons3.accesskeyid}")
    private String accesskeyid;
    @Value("${amazons3.secretaccesskey}")
    private String secretaccesskey;

    private AmazonS3 s3client;

    public AmazonS3Utils() {
        AWSCredentials credentials = new BasicAWSCredentials("AKIAIRMRCRJVQUAL25PA", "gxF7SFkcqcPfnaZMullFiMOKC1jVQdLQx32rxvYd");
        s3client = new AmazonS3Client(credentials);
    }

    public void putDepthPriceRaw(DepthPriceRaw depthPriceRaw) {

    }

    public void putTradeHistoryRaw(TradeHistoryRaw tradeHistoryRaw) {

    }

}
