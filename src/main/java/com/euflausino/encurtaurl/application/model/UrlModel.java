package com.euflausino.encurtaurl.application.model;

import java.time.Instant;

public class UrlModel {

    private String original_url;
    private String short_url;
    private Instant created_at;

    public UrlModel(String original_url, String short_url) {
        this.original_url = original_url;
        this.short_url = short_url;
        this.created_at = Instant.now();
    }

    public UrlModel() {
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

    public void setCreated_at(Instant timestamp) {
        this.created_at = timestamp;
    }
}
