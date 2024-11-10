package com.app.urlshortener.repository;



import com.app.urlshortener.entity.Url;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository("mongoUrlRepository")
public interface UrlRepository extends MongoRepository<Url,String> {
    Optional<Url> findByShortCode(String shortCode);
}
