package com.euflausino.encurtaurl.application.usecase;

import com.euflausino.encurtaurl.application.model.UrlModel;
import com.euflausino.encurtaurl.application.ports.input.ICreateInput;
import com.euflausino.encurtaurl.application.ports.input.IRedirectInput;
import com.euflausino.encurtaurl.application.ports.output.IFindOutput;
import com.euflausino.encurtaurl.application.ports.output.ISaveOutput;
import org.hashids.Hashids;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

public class UrlUseCase implements ICreateInput, IRedirectInput {

    private final Hashids hashids;
    private final RedisTemplate<String, String> redisTemplate;
    private final ClickUseCase clickUseCase;
    private final IFindOutput findOutput;
    private final ISaveOutput saveOutput;

    public UrlUseCase(Hashids hashids, RedisTemplate<String, String> redisTemplate, ClickUseCase clickUseCase, IFindOutput findOutput, ISaveOutput saveOutput) {
        this.hashids = hashids;
        this.redisTemplate = redisTemplate;
        this.clickUseCase = clickUseCase;
        this.findOutput = findOutput;
        this.saveOutput = saveOutput;
    }

    @Override
    public String createShortUrl(String urlOriginal) {
        String code = generateCode();
        UrlModel urlModel = new UrlModel();
        urlModel.setOriginal_url(urlOriginal);
        urlModel.setShort_url(code);
        saveOutput.save(urlModel);

        String cacheKey = "short_url:" + code;
        redisTemplate.opsForValue().set(cacheKey, urlModel.getOriginal_url(), 10, TimeUnit.MINUTES);
        clickUseCase.initializeCount(code);

        return "http://localhost:8080/url/"+code;
    }

    @Override
    public String redirectShortUrl(String code) {
        String cacheKey =   "short_url:" + code;
        String clicks =  "clicks:" + code;

        String url = redisTemplate.opsForValue().get(cacheKey);
        if (url == null){
            String urlOrig = findOutput.find(code);
            redisTemplate.opsForValue().set(cacheKey, urlOrig, 10, TimeUnit.MINUTES);
        }
        clickUseCase.incrementCount(clicks);
        return url;
    }

    private String generateCode(){
        Long id = redisTemplate.opsForValue().increment("url:id");
        return hashids.encode(id);
    }

    private Long decode(String code){
        long[] numbers = hashids.decode(code);
        if (numbers.length == 0) {
            throw new IllegalArgumentException("Invalid code");
        }
        return numbers[0];
    }
}
