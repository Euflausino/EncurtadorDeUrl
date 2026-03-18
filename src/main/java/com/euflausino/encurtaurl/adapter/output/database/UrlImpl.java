package com.euflausino.encurtaurl.adapter.output.database;

import com.euflausino.encurtaurl.adapter.output.database.mapper.OutputMapper;
import com.euflausino.encurtaurl.application.model.UrlModel;
import org.springframework.stereotype.Repository;

@Repository
public class UrlImpl {

    private final UrlRepository urlRepository;

    public UrlImpl(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    public void save(UrlModel url){
        DBUrlEntity dbUrlEntity = OutputMapper.toDBEntity(url);
        urlRepository.save(dbUrlEntity);
    }

    public String find(String code){
        return urlRepository.findById(code)
                .map(DBUrlEntity::getOriginal_url)
                .orElseThrow();
    }

}
