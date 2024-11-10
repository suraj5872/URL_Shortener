package com.app.urlshortener.entity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;
@Entity
@Data
public class UrlCache {
    @Id
    String shortUrl;

    String url;
}