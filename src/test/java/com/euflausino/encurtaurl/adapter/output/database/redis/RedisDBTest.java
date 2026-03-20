package com.euflausino.encurtaurl.adapter.output.database.redis;

import org.hashids.Hashids;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RedisDBTest {

    private RedisTemplate<String, String> redisTemplate;
    private ValueOperations<String, String> valueOperations;
    private Hashids hashids;

    private RedisDB redisDB;

    @BeforeEach
    void setup() {
        redisTemplate = mock(RedisTemplate.class);
        valueOperations = mock(ValueOperations.class);
        hashids = mock(Hashids.class);

        when(redisTemplate.opsForValue()).thenReturn(valueOperations);

        redisDB = new RedisDB(hashids, redisTemplate);
    }

    @Test
    void deveAdicionarNoCacheComTTL() {
        String code = "abc123";
        String url = "https://google.com";

        redisDB.adicionarEmCache(code, url);

        verify(valueOperations).set(
                eq("short_url:" + code),
                eq(url),
                eq(10L),
                eq(TimeUnit.MINUTES)
        );
    }

    @Test
    void deveBuscarDoCache() {
        String code = "abc123";
        String expectedUrl = "https://google.com";

        when(valueOperations.get("short_url:" + code)).thenReturn(expectedUrl);

        String result = redisDB.buscarEmCache(code);

        assertEquals(expectedUrl, result);
    }

    @Test
    void deveGerarCodigoUsandoHashids() {
        Long id = 1L;
        String encoded = "abc123";

        when(valueOperations.increment("url:id")).thenReturn(id);
        when(hashids.encode(id)).thenReturn(encoded);

        String result = redisDB.generateCode();

        assertEquals(encoded, result);

        verify(valueOperations).increment("url:id");
        verify(hashids).encode(id);
    }

    @Test
    void deveDecodificarCodigoValido() {
        String code = "abc123";
        long[] decodedArray = {1L};

        when(hashids.decode(code)).thenReturn(decodedArray);

        Long result = redisDB.decode(code);

        assertEquals(1L, result);
    }

    @Test
    void deveLancarExcecaoParaCodigoInvalido() {
        String code = "invalido";

        when(hashids.decode(code)).thenReturn(new long[]{});

        assertThrows(IllegalArgumentException.class, () -> {
            redisDB.decode(code);
        });
    }
}