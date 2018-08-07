package com.myweb.amazon.s3;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;

import java.io.File;

public class AmazonS3Example {

    private static final String SUFFIX = "/";

    public static void main(String[] args) {
        AWSCredentials credentials = new BasicAWSCredentials("AKIAIRMRCRJVQUAL25PA", "gxF7SFkcqcPfnaZMullFiMOKC1jVQdLQx32rxvYd");
        AmazonS3 s3client = new AmazonS3Client(credentials);
        String bucketName = "dev-pkg-store";
        for (Bucket bucket : s3client.listBuckets()) {
            System.out.println(" - " + bucket.getName());
        }
        // create folder into bucket
        String folderName = "data-lake";

        // upload file to folder and set it to public
        String fileName = folderName + SUFFIX + "data-lake-1.0-SNAPSHOT.jar";
        s3client.putObject(new PutObjectRequest(bucketName, fileName, new File("C:\\Users\\Administrator\\Desktop\\data-lake-1.0-SNAPSHOT.jar")).withCannedAcl(CannedAccessControlList.PublicRead));
    }
}