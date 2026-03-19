package com.euflausino.encurtaurl.application.ports.input;

import com.euflausino.encurtaurl.application.model.UrlModel;

public interface ICreateInput {

    String createShortUrl(String urlOriginal);

}
