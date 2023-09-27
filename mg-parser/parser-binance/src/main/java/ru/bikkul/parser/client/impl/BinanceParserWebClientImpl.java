package ru.bikkul.parser.client.impl;

import com.binance.api.client.domain.market.Candlestick;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import ru.bikkul.parser.client.BinanceParserClient;
import ru.bikkul.parser.config.BinanceApiProvider;
import ru.bikkul.parser.domain.coin.CoinInfo;

import java.util.List;

@Component("webClient")
public class BinanceParserWebClientImpl implements BinanceParserClient {
    private final WebClient webClient;
    private final BinanceApiProvider provider;

    @Value("${binance.api.kline_uri}")
    private String KLINE_URI;

    @Autowired
    public BinanceParserWebClientImpl(@Value("${binance.api.base_url}") String baseUrl, BinanceApiProvider provider) {
        this.provider = provider;
        this.webClient = WebClient.create(baseUrl);
    }

    @Override
    public List<Candlestick> getKlineForFiveMin(String pair, String interval, Long start, Long end, Integer limit) {
        return webClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path(KLINE_URI)
                        .queryParam("symbol", pair)
                        .queryParam("interval", interval)
                        .queryParam("startTime", start)
                        .queryParam("endTime", end)
                        .queryParam("limit", limit)
                        .build())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<Candlestick>>() {
                })
                .block();
    }

    @Override
    public List<CoinInfo> getCoinsInformation(Long timestamp, String signature) {
        String API_HEADER_NAME = "X-MBX-APIKEY";
        return webClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/sapi/v1/capital/config/getall")
                        .queryParam("timestamp", timestamp)
                        .queryParam("signature", signature)
                        .build())
                .header(API_HEADER_NAME, provider.getApiKey())
                .retrieve()
                .bodyToFlux(new ParameterizedTypeReference<CoinInfo>() {
                })
                .collectList()
                .block();
    }
}
