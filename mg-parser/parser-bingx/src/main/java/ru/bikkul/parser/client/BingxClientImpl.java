package ru.bikkul.parser.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class BingxClientImpl implements BingxClient {
    private final WebClient webClient;
    private final String KLINE_URI;

    public BingxClientImpl(@Value("${bingx.api.base_url}") String url,
                           @Value("${bingx.api.kline_uri}") String klineUri) {
        this.webClient = WebClient.create(url);
        this.KLINE_URI = klineUri;
    }

    public String getKline(String symbol, String interval, long start, long end) {
        return webClient
                .get()
                .uri(uriBuilder -> uriBuilder.path(KLINE_URI)
                        .queryParam("symbol", symbol)
                        .queryParam("klineType", interval)
                        .queryParam("startTs", start)
                        .queryParam("endTs", end)
                        .build())
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

}
