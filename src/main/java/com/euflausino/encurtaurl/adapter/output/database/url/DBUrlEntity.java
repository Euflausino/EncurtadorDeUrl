package com.euflausino.encurtaurl.adapter.output.database.url;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.time.Instant;

@Table("urls")
public class DBUrlEntity {

    @PrimaryKey
    private String original_url;
    private String short_url;
    private Instant created_at;

    public DBUrlEntity(String original_url, String short_url, Instant created_at) {
        this.original_url = original_url;
        this.short_url = short_url;
        this.created_at = created_at;
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
        return short_url;
    }

    public void setShort_url(String short_url) {
        this.short_url = short_url;
    }

    public Instant getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Instant created_at) {
        this.created_at = created_at;
    }
}
