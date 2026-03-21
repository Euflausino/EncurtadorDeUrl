package com.euflausino.encurtaurl.config.exceptions;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.util.InvalidUrlException;

import java.net.URISyntaxException;

@RestControllerAdvice
public class ExceptionHandler {


    @org.springframework.web.bind.annotation.ExceptionHandler(UrlNaoEncontradaException.class)
    public ResponseEntity<TimeStampTDO> handleNotFound(UrlNaoEncontradaException ex) {
        return ResponseEntity.status(404).body(new TimeStampTDO(ex.getMessage(), 404));
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(ParamentrosIncorretosException.class)
    public ResponseEntity<TimeStampTDO> handleBadRequest(ParamentrosIncorretosException ex) {
        return ResponseEntity.badRequest().body(new TimeStampTDO(ex.getMessage(), 400));
    }

    @org.springframework.web.bind.annotation.ExceptionHandler
    public ResponseEntity<TimeStampTDO> uriSintaxeException(URISyntaxException ex) {
        return ResponseEntity.badRequest().body(new TimeStampTDO(ex.getMessage(), 400));
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(InvalidCodeException.class)
    public ResponseEntity<TimeStampTDO> handleInvalidCode(InvalidCodeException ex) {
        return ResponseEntity.badRequest().body(new TimeStampTDO(ex.getMessage(), 400));
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(InvalidUrlException.class)
    public ResponseEntity<TimeStampTDO> handleInvalidUrl(InvalidUrlException ex) {
        return ResponseEntity.badRequest().body(new TimeStampTDO(ex.getMessage(), 400));
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(CodeGenerationException.class)
    public ResponseEntity<TimeStampTDO> handleCodeError(CodeGenerationException ex) {
        return ResponseEntity.status(500).body(new TimeStampTDO(ex.getMessage(), 500));
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(org.springframework.dao.DataAccessException.class)
    public ResponseEntity<TimeStampTDO> handleDatabase() {
        return ResponseEntity.status(500).body(new TimeStampTDO("Erro de banco de dados", 500));
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(org.springframework.data.redis.RedisConnectionFailureException.class)
    public ResponseEntity<TimeStampTDO> handleRedis() {
        return ResponseEntity.status(503).body(new TimeStampTDO("Redis indisponível", 503));
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    public ResponseEntity<TimeStampTDO> handleGeneric() {
        return ResponseEntity.status(500).body(new TimeStampTDO("Erro interno", 500));
    }

}
