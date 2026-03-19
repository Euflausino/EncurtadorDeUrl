package com.euflausino.encurtaurl.application.usecase;

import org.springframework.data.redis.core.RedisTemplate;

import java.util.Set;

public class ClickUseCase {

    private final RedisTemplate<String, String> redisTemplate;

    public ClickUseCase(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void incrementCount(String code){
        redisTemplate.opsForValue().increment(code);
    }

    public void initializeCount(String code){
        redisTemplate.opsForValue().setIfAbsent("clicks:" + code, "0");
    }

    public Long getClicks(String code) {
        String value = redisTemplate.opsForValue().get("clicks:" + code);
        return value != null ? Long.parseLong(value) : 0L; //consulta no banco de dados cassandra no lugar do 0L
    }

    //Fazer schedule automatico para rodar a cada 10 minutos e sincronizar os clicks do Redis para o Cassandra
    public void syncClicks() {
        Set<String> keys = redisTemplate.keys("clicks:*");
        for (String key : keys) {

            String code = key.replace("clicks:", "");
            String value = redisTemplate.opsForValue().get(key);

            long clicks = value != null ? Long.parseLong(value) : 0L;

            // salvar no Cassandra (ex: tabela de métricas)
            System.out.println(code + " -> " + clicks);
        }
    }

}
