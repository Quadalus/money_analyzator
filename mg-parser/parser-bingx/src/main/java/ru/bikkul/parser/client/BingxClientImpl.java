package ru.bikkul.parser.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class BingxClientImpl implements BingxClient {
    private final WebClient webClient;

    public BingxClientImpl(@Value("${bingx.api.base_url}") java.lang.String url) {
        this.webClient = WebClient.create(url);
    }

    public String getKline(String symbol, String interval, long start, long end) {
        return webClient
                .get()
                .uri(uriBuilder -> uriBuilder.path("/api/v1/market/getHistoryKlines")
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
