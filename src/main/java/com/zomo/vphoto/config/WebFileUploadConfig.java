package com.zomo.vphoto.config;

import com.google.gson.Gson;
import com.qiniu.common.Zone;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebFileUploadConfig {
    @Value("${qiniu.AccessKey}")
    private String accessKey;

    @Value("${qiniu.SecretKey}")
    private String secretKey;

    @Bean
    public com.qiniu.storage.Configuration qiniuConfig(){
        return new com.qiniu.storage.Configuration(Zone.zone1());
    }
    @Bean
    public UploadManager uploadManager(){
        return new UploadManager(qiniuConfig());
    }
    @Bean
    public Auth auth(){
        return Auth.create(accessKey,secretKey);
    }
    @Bean
    public BucketManager bucketManager(){
        return new BucketManager(auth(),qiniuConfig());
    }

    @Bean
    public Gson gson(){
        return new Gson();
    }
}
