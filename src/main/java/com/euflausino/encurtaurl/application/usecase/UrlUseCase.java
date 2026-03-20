package com.euflausino.encurtaurl.application.usecase;

import com.euflausino.encurtaurl.application.model.UrlModel;
import com.euflausino.encurtaurl.application.ports.input.ICreateInput;
import com.euflausino.encurtaurl.application.ports.input.IRedirectInput;
import com.euflausino.encurtaurl.application.ports.output.*;

public class UrlUseCase implements ICreateInput, IRedirectInput {

    private final IFindOutput findOutput;
    private final ISaveOutput saveOutput;
    private final IAdicionarEmCacheOutput adicionarEmCache;
    private final IBuscaCacheOutput buscaCacheOutput;
    private final IGenerateCodeOutput generateCodeOutput;

    public UrlUseCase(IFindOutput findOutput, ISaveOutput saveOutput, IAdicionarEmCacheOutput adicionarEmCache, IBuscaCacheOutput buscaCacheOutput, IGenerateCodeOutput generateCodeOutput) {
        this.findOutput = findOutput;
        this.saveOutput = saveOutput;
        this.adicionarEmCache = adicionarEmCache;
        this.buscaCacheOutput = buscaCacheOutput;
        this.generateCodeOutput = generateCodeOutput;
    }

    @Override
    public String createShortUrl(String urlOriginal) {
        String code =  generateCodeOutput.generateCode();
        UrlModel urlModel = new UrlModel();
        urlModel.setOriginal_url(urlOriginal);
        urlModel.setShort_url(code);
        saveOutput.save(urlModel);

        adicionarEmCache.adicionarEmCache(code, urlModel.getOriginal_url());

        return "http://localhost:8080/url/"+code;
    }

    @Override
    public String redirectShortUrl(String code) {
        String url = buscaCacheOutput.buscarEmCache(code);
        if (url == null){
            url = findOutput.find(code);
            adicionarEmCache.adicionarEmCache(code, url);
        }
        return url;
    }

}
