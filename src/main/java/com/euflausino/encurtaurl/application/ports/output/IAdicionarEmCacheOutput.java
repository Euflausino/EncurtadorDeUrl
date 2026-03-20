package com.euflausino.encurtaurl.application.ports.output;

public interface IAdicionarEmCacheOutput {

    void adicionarEmCache(String code, String originalUrl);

}
