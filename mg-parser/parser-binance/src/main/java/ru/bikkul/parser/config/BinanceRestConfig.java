package ru.bikkul.parser.config;

import com.binance.api.client.BinanceApiClientFactory;
import com.binance.api.client.BinanceApiRestClient;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BinanceRestConfig {
    private final BinanceApiProvider binanceApiProvider;

    @Bean
    public BinanceApiRestClient binanceApiRestClient() {
        return BinanceApiClientFactory.newInstance(binanceApiProvider.getApiKey(), binanceApiProvider.getApiSecret()).newRestClient();
    }
}
