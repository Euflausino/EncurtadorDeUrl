package com.euflausino.encurtaurl.application.model;

import java.time.LocalDateTime;

public class UrlModel {

    private String original_url;
    private String short_url;
    private LocalDateTime created_at;

    public UrlModel(String original_url, String short_url, LocalDateTime timestamp) {
        this.original_url = original_url;
        this.short_url = short_url;
        this.created_at = timestamp;
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

    public LocalDateTime getTimestamp() {
        return created_at;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.created_at = timestamp;
    }
}
