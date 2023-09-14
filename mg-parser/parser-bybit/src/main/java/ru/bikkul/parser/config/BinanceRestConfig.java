package ru.bikkul.parser.config;

import com.binance.api.client.BinanceApiClientFactory;
import com.binance.api.client.BinanceApiRestClient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BinanceRestConfig {
    @Value(value = "${binance.api.key}")
    private String API_KEY;

    @Value(value = "${binance.api.secret}")
    private String API_SECRET;

    @Bean
    public BinanceApiRestClient binanceApiRestClient() {
        return BinanceApiClientFactory.newInstance(API_KEY, API_SECRET).newRestClient();
    }
}
