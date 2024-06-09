package com.dev.backend.configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AmazonS3Configuration {

    @Value("${security.aws.secretAccessKey}")
    private String secretAccessKey;

    @Value("${security.aws.accessKeyId}")
    private String accessKeyId;

    @Value("${security.aws.region}")
    private String region;

    @Bean
    public AmazonS3 getAmazonS3Client() {

        final BasicAWSCredentials basicAWSCredentials = new BasicAWSCredentials(accessKeyId, secretAccessKey);

        return AmazonS3ClientBuilder
                .standard()
                .withRegion(Regions.fromName(region))
                .withCredentials(new AWSStaticCredentialsProvider(basicAWSCredentials))
                .build();
    }
}
