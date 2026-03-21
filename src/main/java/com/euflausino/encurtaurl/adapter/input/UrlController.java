package com.euflausino.encurtaurl.adapter.input;

import com.euflausino.encurtaurl.application.ports.input.ICreateInput;
import com.euflausino.encurtaurl.application.ports.input.IRedirectInput;
import com.euflausino.encurtaurl.config.exceptions.ParamentrosIncorretosException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/urls")
public class UrlController {

    @Value("${api.host}")
    private String host;
    @Value("${api.port}")
    private int port;
    private final ICreateInput createInput;
    private final IRedirectInput redirectInput;

    public UrlController(
            ICreateInput createInput,
            IRedirectInput redirectInput) {
        this.createInput = createInput;
        this.redirectInput = redirectInput;
    }

    @PostMapping
    ResponseEntity<String> createShortUrl(@RequestParam String originalUrl) throws URISyntaxException {
        if(originalUrl == null || !isValidUrl(originalUrl)) {
            throw new ParamentrosIncorretosException("originalUrl nulo ou inválido!");
        }
        String shortUrl = createInput.createShortUrl(originalUrl);
        URI uri;
        try {
            uri = new URI("http",null, host, port, "/urls/" + shortUrl, null, null);
        } catch (URISyntaxException e) {
            throw new URISyntaxException("Erro ao gerar URI", e.getMessage());
        }
        return ResponseEntity.created(uri).build();
    }

    @GetMapping("/{code}")
    ResponseEntity<Void> redirectShortUrl(@PathVariable String code) {
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(redirectInput.redirectShortUrl(code)))
                .build();
    }

    private boolean isValidUrl(String url) {
        try {
            URI uri = new URI(url);
            return uri.getScheme() != null &&
                    uri.getHost() != null &&
                    (uri.getScheme().equals("http") || uri.getScheme().equals("https"));
        } catch (Exception e) {
            return false;
        }
    }

}
