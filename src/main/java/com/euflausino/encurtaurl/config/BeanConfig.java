package com.euflausino.encurtaurl.config;

import org.hashids.Hashids;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean
    public Hashids hashids(HashidsProperties props) {
        return new Hashids(props.getSalt(), props.getLength());
    }

}
