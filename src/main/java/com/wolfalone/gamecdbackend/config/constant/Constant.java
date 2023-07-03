package com.wolfalone.gamecdbackend.config.constant;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "app")
public class Constant {

    private final AWS aws = new AWS();

    public static final class AWS {
        private String accessKey;
        private String secretKey;

        private String s3Url;

        public String getS3Url() {
            return s3Url;
        }

        public void setS3Url(String s3Url) {
            this.s3Url = s3Url;
        }

        public String getAccessKey() {
            return accessKey;
        }

        public void setAccessKey(String accessKey) {
            this.accessKey = accessKey;
        }

        public String getSecretKey() {
            return secretKey;
        }

        public void setSecretKey(String secretKey) {
            this.secretKey = secretKey;
        }
    }

    public AWS getAws(){
        return aws;
    }
}
