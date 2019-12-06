package pl.softwareplant.api.client.cache;

import com.github.benmanes.caffeine.cache.AsyncLoadingCache;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.softwareplant.api.client.dto.FilmDetailsDto;

import java.util.concurrent.TimeUnit;

@Configuration
public class CaffeineCache {

    @Bean
    Cache<Integer, String> planetsCache() {
        return Caffeine.newBuilder().build();
    }

    @Bean
    Cache<String, FilmDetailsDto> filmsCache() {
        return Caffeine.newBuilder()
                .expireAfterWrite(10, TimeUnit.MINUTES)
                .build();
    }

    @Bean
    AsyncLoadingCache<String, FilmDetailsDto> cache() {
        return Caffeine.newBuilder()
                .maximumSize(1000)
                .expireAfterWrite(1, TimeUnit.MINUTES)
                .buildAsync((key, executor) -> cache().get(key))
                /*.buildAsync(k -> FilmDetailsDto.builder().build())*/;
    }
}
