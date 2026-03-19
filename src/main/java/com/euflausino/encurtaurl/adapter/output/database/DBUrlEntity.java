package com.euflausino.encurtaurl.adapter.output.database;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.time.Instant;

@Table("urls")
public class DBUrlEntity {

    @PrimaryKey
    private String short_code;

    private String original_url;
    private Instant created_at;

    public DBUrlEntity(String short_code, String original_url) {
        this.short_code = short_code;
        this.original_url = original_url;
        this.created_at = Instant.now();
    }

    public DBUrlEntity() {
    }

    public String getOriginal_url() {
        return original_url;
    }

    public void setOriginal_url(String original_url) {
        this.original_url = original_url;
    }

    public String getShort_url() {
        return short_code;
    }

    public void setShort_url(String short_code) {
        this.short_code = short_code;
    }

    public Instant getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Instant created_at) {
        this.created_at = created_at;
    }
}
