package com.euflausino.encurtaurl.adapter.output.database.url.mapper;

import com.euflausino.encurtaurl.adapter.output.database.url.DBUrlEntity;
import com.euflausino.encurtaurl.application.model.UrlModel;

public class OutputMapper {

    public static DBUrlEntity toDBEntity(UrlModel entity) {
        return new DBUrlEntity(
            entity.getShort_url(),
            entity.getOriginal_url()
        );
    }
}
