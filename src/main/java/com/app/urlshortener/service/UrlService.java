package com.app.urlshortener.service;


import com.app.urlshortener.entity.Url;
import com.app.urlshortener.repository.UrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class UrlService {
    @Autowired
    private final UrlRepository urlRepository;

    public UrlService(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    public Url createShortUrl(String url) {
        Url newUrl = new Url();
        newUrl.setUrl(url);
        newUrl.setShortCode(generateShortCode());
        newUrl.setCreatedAt(LocalDateTime.now());
        newUrl.setUpdatedAt(LocalDateTime.now());
        newUrl.setAccessCount(0);
        return urlRepository.save(newUrl);
    }

    public Optional<Url> getOriginalUrl(String shortCode) {
        Optional<Url> urlOpt = urlRepository.findByShortCode(shortCode);
        urlOpt.ifPresent(url -> {
            url.setAccessCount(url.getAccessCount() + 1);
            urlRepository.save(url);
        });
        return urlOpt;
    }

    public Optional<Url> updateUrl(String shortCode, String newUrl) {
        Optional<Url> urlOpt = urlRepository.findByShortCode(shortCode);
        urlOpt.ifPresent(url -> {
            url.setUrl(newUrl);
            url.setUpdatedAt(LocalDateTime.now());
            urlRepository.save(url);
        });
        return urlOpt;
    }

    public boolean deleteUrl(String shortCode) {
        Optional<Url> urlOpt = urlRepository.findByShortCode(shortCode);
        urlOpt.ifPresent(urlRepository::delete);
        return urlOpt.isPresent();
    }

    public Optional<Url> getUrlStatistics(String shortCode) {
        return urlRepository.findByShortCode(shortCode);
    }

    private String generateShortCode() {
        return UUID.randomUUID().toString().substring(0, 6);
    }
}
