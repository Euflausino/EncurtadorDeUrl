package com.euflausino.encurtaurl.config;

import com.euflausino.encurtaurl.application.ports.output.IFindOutput;
import com.euflausino.encurtaurl.application.ports.output.ISaveOutput;
import com.euflausino.encurtaurl.application.usecase.UrlUseCase;
import org.hashids.Hashids;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class BeanConfig {

    @Bean
    public Hashids hashids(HashidsProperties props) {
        return new Hashids(props.getSalt(), props.getLength());
    }

    @Bean
    public UrlUseCase urlUseCase(Hashids hashids, RedisTemplate<String, String> redisTemplate, ClickUseCase clickUseCase, IFindOutput findOutput, ISaveOutput saveOutput) {
        return new UrlUseCase(hashids, redisTemplate, clickUseCase, findOutput, saveOutput);
    }

    @Bean
    public ClickUseCase clickUseCase(RedisTemplate<String, String> redisTemplate) {
        return new ClickUseCase(redisTemplate);
    }

}
