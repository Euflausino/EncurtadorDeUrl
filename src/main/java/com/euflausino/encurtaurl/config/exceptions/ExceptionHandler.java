package com.euflausino.encurtaurl.config.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.util.InvalidUrlException;

@RestControllerAdvice
public class ExceptionHandler {


    @org.springframework.web.bind.annotation.ExceptionHandler(UrlNaoEncontradaException.class)
    public ResponseEntity<?> handleNotFound(UrlNaoEncontradaException ex) {
        return ResponseEntity.status(404).body(new TimeStampTDO(ex.getMessage(), 404));
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(InvalidCodeException.class)
    public ResponseEntity<?> handleInvalidCode(InvalidCodeException ex) {
        return ResponseEntity.badRequest().body(new TimeStampTDO(ex.getMessage(), 400));
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(InvalidUrlException.class)
    public ResponseEntity<?> handleInvalidUrl(InvalidUrlException ex) {
        return ResponseEntity.badRequest().body(new TimeStampTDO(ex.getMessage(), 400));
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(CodeGenerationException.class)
    public ResponseEntity<?> handleCodeError(CodeGenerationException ex) {
        return ResponseEntity.status(500).body(new TimeStampTDO(ex.getMessage(), 500));
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(org.springframework.dao.DataAccessException.class)
    public ResponseEntity<?> handleDatabase() {
        return ResponseEntity.status(500).body(new TimeStampTDO("Erro de banco de dados", 500));
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(org.springframework.data.redis.RedisConnectionFailureException.class)
    public ResponseEntity<?> handleRedis() {
        return ResponseEntity.status(503).body(new TimeStampTDO("Redis indisponível", 503));
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGeneric() {
        return ResponseEntity.status(500).body(new TimeStampTDO("Erro interno", 500));
    }

}
