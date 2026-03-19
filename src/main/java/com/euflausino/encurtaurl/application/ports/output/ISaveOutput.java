package com.euflausino.encurtaurl.application.ports.output;

import com.euflausino.encurtaurl.application.model.UrlModel;

public interface ISaveOutput {
    void save(UrlModel url);
}
