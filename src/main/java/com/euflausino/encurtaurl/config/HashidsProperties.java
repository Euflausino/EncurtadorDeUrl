package com.euflausino.encurtaurl.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "hashids")
public class HashidsProperties {

    private String salt;
    private int length;

    public HashidsProperties(String salt, int length) {
        this.salt = salt;
        this.length = length;
    }

    public HashidsProperties() {
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }
}