package pl.softwareplant.api.client.config;

import feign.Logger;
import feign.okhttp.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.concurrent.TimeUnit;

@Configuration
public class ClientConfig {
    @Bean
    public Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

    @Bean
    public OkHttpClient client() {
        Proxy proxy = new Proxy(Proxy.Type.HTTP,
                new InetSocketAddress("gateway.zscaler.net", 80));

        okhttp3.OkHttpClient builder = new okhttp3.OkHttpClient.Builder()
                .proxy(proxy).connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build();
        return new OkHttpClient(builder);
    }
}
