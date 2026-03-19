package com.euflausino.encurtaurl.application.model;

import java.time.Instant;

public class ClickModel {

    private String short_code;
    private Instant event_time;

    public ClickModel(String short_code, Instant event_time) {
        this.short_code = short_code;
        this.event_time = event_time;
    }

    public String getShort_code() {
        return short_code;
    }

    public void setShort_code(String short_code) {
        this.short_code = short_code;
    }

    public Instant getEvent_time() {
        return event_time;
    }

    public void setEvent_time(Instant event_time) {
        this.event_time = event_time;
    }
}
