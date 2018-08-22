package com.myweb.s3;


import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class AmazonS3Utils {
    @Value("${amazons3.accesskey}")
    private String accesskeyid;
    @Value("${amazons3.secretaccesskey}")
    private String secretaccesskey;

    private AmazonS3 s3client;

    private static final String SUFFIX = "/";

    public AmazonS3Utils() {
        AWSCredentials credentials = new BasicAWSCredentials(accesskeyid, secretaccesskey);
        s3client = new AmazonS3Client(credentials);
    }




    public static void main(String[] args) {
        AWSCredentials credentials = new BasicAWSCredentials("AKIAJLUBGQ46EJB26NTA", "+SWt5HdBqPJPic7EzHaH+Ln/HMy2TECOPjNQIPns");
        AmazonS3 s3client2 = new AmazonS3Client(credentials);
        String bucketName = "dev-pkg-store";
        for (Bucket bucket : s3client2.listBuckets()) {
            System.out.println(" - " + bucket.getName());
        }
        // create folder into bucket
        String folderName = "data-lake";

        // upload file to folder and set it to public
        String fileName = folderName + SUFFIX + "aaaa.avro";
        s3client2.putObject(new PutObjectRequest(bucketName, fileName, new File("C:\\Users\\Administrator\\Desktop\\aaaa.avro")));
    }
}