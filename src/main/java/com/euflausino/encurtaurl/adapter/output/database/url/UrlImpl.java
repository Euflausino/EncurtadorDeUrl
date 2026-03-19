package com.euflausino.encurtaurl.adapter.output.database.url;

import com.euflausino.encurtaurl.adapter.output.database.url.mapper.OutputMapper;
import com.euflausino.encurtaurl.application.model.UrlModel;
import com.euflausino.encurtaurl.application.ports.output.IFindOutput;
import com.euflausino.encurtaurl.application.ports.output.ISaveOutput;
import org.springframework.stereotype.Repository;

@Repository
public class UrlImpl implements ISaveOutput, IFindOutput {

    private final UrlRepository urlRepository;

    public UrlImpl(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    @Override
    public void save(UrlModel url){
        DBUrlEntity dbUrlEntity = OutputMapper.toDBEntity(url);
        urlRepository.save(dbUrlEntity);
    }

    @Override
    public String find(String code){
        return urlRepository.findById(code)
                .map(DBUrlEntity::getOriginal_url)
                .orElseThrow();
    }

}
