package com.github.izerui;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.net.InetSocketAddress;
import java.net.Proxy;

@SpringBootApplication
@EnableTransactionManagement(proxyTargetClass = true)
@EnableAsync
public class Application {

    @Bean
    public OkHttpClient okHttpClient() {
        return new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor(s -> System.out.println(s)).setLevel(HttpLoggingInterceptor.Level.BODY))
                .proxy(new Proxy(Proxy.Type.SOCKS, new InetSocketAddress("127.0.0.1", 1080)))
                .build();
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
