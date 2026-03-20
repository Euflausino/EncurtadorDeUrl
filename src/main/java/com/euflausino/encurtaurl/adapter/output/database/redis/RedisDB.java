package com.euflausino.encurtaurl.adapter.output.database.redis;

import com.euflausino.encurtaurl.application.ports.output.IAdicionarEmCacheOutput;
import com.euflausino.encurtaurl.application.ports.output.IBuscaCacheOutput;
import com.euflausino.encurtaurl.application.ports.output.IGenerateCodeOutput;
import org.hashids.Hashids;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

@Repository
public class RedisDB implements IAdicionarEmCacheOutput, IBuscaCacheOutput, IGenerateCodeOutput {

    private final Hashids hashids;
    private final RedisTemplate<String, String> redisTemplate;

    public RedisDB(Hashids hashids, RedisTemplate<String, String> redisTemplate) {
        this.hashids = hashids;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void adicionarEmCache(String code, String originalUrl) {
        String cacheKey = "short_url:" + code;
        redisTemplate.opsForValue().set(cacheKey, originalUrl, 10, TimeUnit.MINUTES);
    }

    @Override
    public String buscarEmCache(String code) {
        String cacheKey =   "short_url:" + code;
        return redisTemplate.opsForValue().get(cacheKey);
    }

    @Override
    public String generateCode(){
        Long id = redisTemplate.opsForValue().increment("url:id");
        return hashids.encode(id);
    }

    public Long decode(String code){
        long[] numbers = hashids.decode(code);
        if (numbers.length == 0) {
            throw new IllegalArgumentException("Invalid code");
        }
        return numbers[0];
    }

}
