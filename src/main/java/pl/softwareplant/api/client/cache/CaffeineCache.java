package pl.softwareplant.api.client.cache;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CaffeineCache {

    @Bean
    Cache<Integer, String> planetsCache() {
        return Caffeine.newBuilder().build();
    }

    @Bean
    Cache<Integer, String> filmsCache() {
        return Caffeine.newBuilder().build();
    }

}
