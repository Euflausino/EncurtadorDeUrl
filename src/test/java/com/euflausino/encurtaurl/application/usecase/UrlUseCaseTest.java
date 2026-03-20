package com.euflausino.encurtaurl.application.usecase;

import com.euflausino.encurtaurl.application.model.UrlModel;
import com.euflausino.encurtaurl.application.ports.output.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class UrlUseCaseTest {

    private IFindOutput findOutput;
    private ISaveOutput saveOutput;
    private IAdicionarEmCacheOutput adicionarEmCache;
    private IBuscaCacheOutput buscaCacheOutput;
    private IGenerateCodeOutput generateCodeOutput;

    private UrlUseCase urlUseCase;

    @BeforeEach
    void setup() {
        findOutput = mock(IFindOutput.class);
        saveOutput = mock(ISaveOutput.class);
        adicionarEmCache = mock(IAdicionarEmCacheOutput.class);
        buscaCacheOutput = mock(IBuscaCacheOutput.class);
        generateCodeOutput = mock(IGenerateCodeOutput.class);

        urlUseCase = new UrlUseCase(
                findOutput,
                saveOutput,
                adicionarEmCache,
                buscaCacheOutput,
                generateCodeOutput
        );
    }

    @Test
    void deveCriarUrlCurta() {
        String originalUrl = "https://google.com";
        String code = "abc123";

        when(generateCodeOutput.generateCode()).thenReturn(code);

        String result = urlUseCase.createShortUrl(originalUrl);

        assertEquals("http://localhost:8080/url/" + code, result);

        verify(saveOutput, times(1)).save(any(UrlModel.class));
        verify(adicionarEmCache, times(1)).adicionarEmCache(code, originalUrl);
    }

    @Test
    void deveRedirecionarUsandoCache() {
        String code = "abc123";
        String cachedUrl = "https://google.com";

        when(buscaCacheOutput.buscarEmCache(code)).thenReturn(cachedUrl);

        String result = urlUseCase.redirectShortUrl(code);

        assertEquals(cachedUrl, result);

        verify(findOutput, never()).find(any());
        verify(adicionarEmCache, never()).adicionarEmCache(any(), any());
    }

    @Test
    void deveRedirecionarBuscandoNoBancoQuandoCacheVazio() {
        String code = "abc123";
        String dbUrl = "https://google.com";

        when(buscaCacheOutput.buscarEmCache(code)).thenReturn(null);
        when(findOutput.find(code)).thenReturn(dbUrl);

        String result = urlUseCase.redirectShortUrl(code);

        assertEquals(dbUrl, result);

        verify(findOutput, times(1)).find(code);
        verify(adicionarEmCache, times(1)).adicionarEmCache(code, dbUrl);
    }
}