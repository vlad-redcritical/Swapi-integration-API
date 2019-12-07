package pl.softwareplant.api.client.cache;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class CaffeineConfig {

    @Bean
    Cache<String, String> planetHomeWordCache() {
        return Caffeine.newBuilder()
                .expireAfterWrite(4, TimeUnit.MINUTES)
                .build();
    }

    @Bean
    Cache<String, String> filmsDetailsCache() {
        return Caffeine.newBuilder()
                .expireAfterWrite(4, TimeUnit.MINUTES)
                .build();
    }
}