package com.euflausino.encurtaurl.config;

import com.euflausino.encurtaurl.application.ports.output.*;
import com.euflausino.encurtaurl.application.usecase.UrlUseCase;
import org.hashids.Hashids;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean
    public Hashids hashids(HashidsProperties props) {
        return new Hashids(props.getSalt(), props.getLength());
    }

    @Bean
    public UrlUseCase urlUseCase(IFindOutput findOutput, ISaveOutput saveOutput, IAdicionarEmCacheOutput adicionarEmCache, IBuscaCacheOutput buscaCacheOutput, IGenerateCodeOutput generateCodeOutput) {
        return new UrlUseCase(findOutput, saveOutput, adicionarEmCache, buscaCacheOutput, generateCodeOutput);
    }

}
