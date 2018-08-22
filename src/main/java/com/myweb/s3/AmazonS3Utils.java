package com.myweb.s3;


import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;

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

    public void createFile(String bucketName, String fileName, File file) {
        s3client.putObject(bucketName, fileName, file);
    }

    public File getFile(String bucketName, String fileName) throws Exception {
        S3ObjectInputStream s3is = s3client.getObject(bucketName, fileName).getObjectContent();
        File file = new File(fileName);
        FileOutputStream fos = new FileOutputStream(file);
        byte[] read_buf = new byte[1024];
        int read_len = 0;
        while ((read_len = s3is.read(read_buf)) > 0) {
            fos.write(read_buf, 0, read_len);
        }
        s3is.close();
        fos.close();
        return file;
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