package com.euflausino.encurtaurl.adapter.input;

import com.euflausino.encurtaurl.application.ports.input.ICreateInput;
import com.euflausino.encurtaurl.application.ports.input.IRedirectInput;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/url")
public class UrlController {

    private final ICreateInput createInput;
    private final IRedirectInput redirectInput;

    public UrlController(ICreateInput createInput, IRedirectInput redirectInput) {
        this.createInput = createInput;
        this.redirectInput = redirectInput;
    }

    @PostMapping
    ResponseEntity<String> createShortUrl(@RequestParam String originalUrl) {
        String shortUrl = createInput.createShortUrl(originalUrl);
        return ResponseEntity.ok(shortUrl);
    }

    @GetMapping("/{code}")
    ResponseEntity<Void> redirectShortUrl(@PathVariable String code) {
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(redirectInput.redirectShortUrl(code)))
                .build();
    }

}
