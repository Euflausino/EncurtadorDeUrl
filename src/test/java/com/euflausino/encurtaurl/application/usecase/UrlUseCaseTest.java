package com.euflausino.encurtaurl.application.usecase;

import com.euflausino.encurtaurl.application.model.UrlModel;
import com.euflausino.encurtaurl.application.ports.output.IFindOutput;
import com.euflausino.encurtaurl.application.ports.output.ISaveOutput;
import org.hashids.Hashids;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UrlUseCaseTest {

    @Mock
    private Hashids hashids;
    @Mock
    private RedisTemplate<String, String> redisTemplate;
    @Mock
    private IFindOutput findOutput;
    @Mock
    private ISaveOutput saveOutput;
    @Mock
    private ValueOperations<String, String> valueOperations;

    @InjectMocks
    private UrlUseCase urlUseCase;


    @BeforeEach
    void setUp() {
        Mockito.when(redisTemplate.opsForValue()).thenReturn(valueOperations);
    }

    @Test
    void deveGerarUmShortCode(){
        Mockito.when(hashids.encode(Mockito.anyLong())).thenReturn("abc123");
        String code = urlUseCase.createShortUrl("https://github.com/Euflausino");
        Assertions.assertNotNull(code);
    }

    @Test
    void deveRetornarUrlDoCache() {
        String code = "abc123";
        String cacheKey = "short_url:" + code;
        Mockito.when(valueOperations.get(cacheKey))
                    .thenReturn("https://google.com");
        String result = urlUseCase.redirectShortUrl(code);
        Assertions.assertEquals("https://google.com", result);
        // não deve buscar no banco
        Mockito.verify(findOutput, Mockito.never()).find(Mockito.any());

    }

    @Test
    void deveBuscarNoBancoESalvarNoCache() {
        String code = "abc123";
        String cacheKey = "short_url:" + code;

        Mockito.when(valueOperations.get(cacheKey))
                .thenReturn(null);

        Mockito.when(findOutput.find(code))
                .thenReturn("https://github.com");

        String result = urlUseCase.redirectShortUrl(code);

        Assertions.assertEquals("https://github.com", result);

        // deve buscar no banco
        Mockito.verify(findOutput).find(code);

        // deve salvar no cache
        Mockito.verify(valueOperations)
                .set(cacheKey, "https://github.com", 10, TimeUnit.MINUTES);
    }

}