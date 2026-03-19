package com.euflausino.encurtaurl.adapter.output.database;

import com.euflausino.encurtaurl.application.model.UrlModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.NoSuchElementException;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class UrlImplTest {

    @Mock
    private UrlRepository urlRepository;

    @InjectMocks
    private UrlImpl urlImpl;

    @Test
    void deveSalvarUrl() {

        UrlModel model = new UrlModel();
        model.setShort_url("abc123");
        model.setOriginal_url("https://google.com");

        urlImpl.save(model);

        Mockito.verify(urlRepository).save(Mockito.any(DBUrlEntity.class));
    }

    @Test
    void deveRetornarUrlQuandoEncontrar() {
        DBUrlEntity entity = new DBUrlEntity();
        entity.setOriginal_url("https://google.com");

        Mockito.when(urlRepository.findById("abc123"))
                .thenReturn(Optional.of(entity));

        String result = urlImpl.find("abc123");

        Assertions.assertEquals("https://google.com", result);
    }

    @Test
    void deveLancarExcecaoQuandoNaoEncontrar() {
        Mockito.when(urlRepository.findById("abc123"))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(NoSuchElementException.class, () -> {
            urlImpl.find("abc123");
        });
    }

}