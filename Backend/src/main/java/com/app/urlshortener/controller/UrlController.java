package com.app.urlshortener.controller;


import com.app.urlshortener.entity.Url;
import com.app.urlshortener.service.UrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/shorten")
@CrossOrigin(origins = "http://localhost:4200")
public class UrlController {

    @Autowired
    private final UrlService urlService;

    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    @PostMapping
    public ResponseEntity<?> createShortUrl(@RequestBody Map<String, String> request) {
        String url = request.get("url");
        if (url == null || url.isEmpty()) {
            return ResponseEntity.badRequest().body("URL is required");
        }
        Url shortUrl = urlService.createShortUrl(url);
        return ResponseEntity.status(HttpStatus.CREATED).body(shortUrl);
    }

    @GetMapping("/{shortCode}")
    public ResponseEntity<Url> getOriginalUrl(@PathVariable String shortCode) {
        Optional<Url> urlOpt = urlService.getOriginalUrl(shortCode);
        return urlOpt.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PutMapping("/{shortCode}")
    public ResponseEntity<Url> updateShortUrl(@PathVariable String shortCode, @RequestBody Map<String, String> request) {
        String newUrl = request.get("url");
        if (newUrl == null || newUrl.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        Optional<Url> urlOpt = urlService.updateUrl(shortCode, newUrl);
        return urlOpt.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @DeleteMapping("/{shortCode}")
    public ResponseEntity<Void> deleteShortUrl(@PathVariable String shortCode) {
        boolean isDeleted = urlService.deleteUrl(shortCode);
        return isDeleted ? ResponseEntity.noContent().build() : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("/{shortCode}/stats")
    public ResponseEntity<Url> getUrlStatistics(@PathVariable String shortCode) {
        Optional<Url> urlOpt = urlService.getUrlStatistics(shortCode);
        return urlOpt.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

}
