package com.myweb.s3;


import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.myweb.domain.DepthPriceRaw;
import com.myweb.domain.TradeHistoryRaw;
import com.myweb.vo.Parameter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class S3Utils {
    @Value("${custom.s3.accesskey}")
    private String accesskeyid = "AKIAJLUBGQ46EJB26NTA";
    @Value("${custom.s3.secretaccesskey}")
    private String secretaccesskey = "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";

    private static final Logger logger = LogManager.getLogger(S3Utils.class);

    private AmazonS3 s3client;

    private static final String SUFFIX = "/";

    public S3Utils() {
        AWSCredentials credentials = new BasicAWSCredentials(accesskeyid, secretaccesskey);
        s3client = new AmazonS3Client(credentials);
    }

    public void putFile(String bucketName, String fileName, File file) {
        s3client.putObject(new PutObjectRequest(bucketName, fileName, file));
    }


    public static File makeTempFile(String dir, String fileName) {
        File dirFile = new File(dir);
        if (!dirFile.exists()) {
            dirFile.mkdir();
        }
        try {
            return File.createTempFile(fileName.split(SUFFIX)[1], ".tmp", dirFile);
        } catch (IOException e) {
            logger.error("MakeTempFile Error");
        }
        return null;
    }

    public static void deleteTempFile(File file) {
        file.delete();
    }

    public File getFile(String bucketName, String dir, String fileName) {
        File file = file = makeTempFile(dir, fileName);
        if (!listAllFileName(bucketName, dir).contains(fileName)) return file;
        S3ObjectInputStream s3is = s3client.getObject(bucketName, fileName).getObjectContent();
        try {
            FileOutputStream fos = new FileOutputStream(file);
            byte[] read_buf = new byte[1024];
            int read_len = 0;
            while ((read_len = s3is.read(read_buf)) > 0) {
                fos.write(read_buf, 0, read_len);
            }
            s3is.close();
            fos.close();
        } catch (Exception e) {
            logger.error("GetFile Error");
        }
        return file;
    }

    public List<String> listAllFileName(String bucketName, String dirname) {
        List<String> list = new ArrayList<>();
        ObjectListing objectListing = s3client.listObjects(bucketName, dirname);
        objectListing.getObjectSummaries().forEach(e -> {
            if (e.getKey().split(SUFFIX).length > 1) list.add(e.getKey());
        });
        return list;
    }

    public static void main(String[] args) {
        S3Utils amazonS3Utils = new S3Utils();
        String bucketName = "dev-pkg-store";
        // create folder into bucket
        String folderName = "data-lake2";
        // upload file to folder and set it to public
        String fileName = folderName + SUFFIX + "bbbccc.avro";
        File file = amazonS3Utils.getFile(bucketName, folderName, fileName);
        System.out.println(file.getName());
        S3Utils.deleteTempFile(file);
    }

    public static String makePath(Parameter parameter) {
        return String.valueOf((int) ((Long) parameter.getEndTimestamp() / (1000 * 60 * 60)));
    }

    public static String makeDepthPriceFileName(Parameter parameter, DepthPriceRaw depthPriceRaw) {
        return makePath(parameter) + SUFFIX + depthPriceRaw.getCounterParty() + "." + depthPriceRaw.getSymbol() + "." + parameter.getStartTimestamp() + "." + parameter.getEndTimestamp() + ".avro";
    }

    public static String makeTradHistoryFileName(Parameter parameter, TradeHistoryRaw tradeHistoryRaw) {
        return makePath(parameter) + SUFFIX + tradeHistoryRaw.getCounterParty() + "." + tradeHistoryRaw.getSymbol() + "." + parameter.getStartTimestamp() + "." + parameter.getEndTimestamp() + ".avro";
    }

    public static List<File> getFile(String path, Parameter parameter) {
        Integer start = (int) ((Long) parameter.getStartTimestamp() / (1000 * 60 * 60));
        if (start <= 426223) start = 426223;
        Integer now = (int) ((Long) new Date().getTime() / (1000 * 60 * 60));
        Integer end = (int) ((Long) parameter.getEndTimestamp() / (1000 * 60 * 60));
        if (end >= now) end = now;
        List<File> files = new ArrayList<>();
        for (int i = 0; start + i <= end; i++) {
            File file = new File(path + File.separator + String.valueOf(start + i));
            if (file.exists() && file.isDirectory()) {
                for (File f : file.listFiles()) {
                    String name = f.getName();
                    if (Long.parseLong(name.split(".")[2]) <= (Long) parameter.getStartTimestamp() && Long.parseLong(name.split(".")[3]) >= (Long) parameter.getStartTimestamp()) {
                        files.add(f);
                    } else if (Long.parseLong(name.split(".")[3]) >= (Long) parameter.getEndTimestamp() && Long.parseLong(name.split(".")[2]) <= (Long) parameter.getEndTimestamp()) {
                        files.add(f);
                    } else if (Long.parseLong(name.split(".")[3]) <= (Long) parameter.getEndTimestamp() && Long.parseLong(name.split(".")[2]) >= (Long) parameter.getStartTimestamp()) {
                        files.add(f);
                    }
                }
            }
        }
        return files;
    }
}