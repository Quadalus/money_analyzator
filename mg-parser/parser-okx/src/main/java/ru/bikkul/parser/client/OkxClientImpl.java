package ru.bikkul.parser.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import ru.bikkul.parser.domain.market.KlineFull;

@Service
public class OkxClientImpl implements OkxClient {
    private final WebClient webClient;

    public OkxClientImpl(@Value("${okx.api.base_url}") String url) {
        this.webClient = WebClient.create(url);
    }

    public KlineFull getKline(String symbol, String interval, Integer limit, long startTime, long endTime) {
        return webClient
                .get()
                .uri(uriBuilder -> uriBuilder.path("/api/v5/market/candles")
                        .queryParam("instType", "SPOT".toUpperCase())
                        .queryParam("instId", symbol)
                        .queryParam("bar", interval)
                        .queryParam("limit", limit)
                        .queryParam("before", startTime)
                        .queryParam("after", endTime)
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(KlineFull.class)
                .block();
    }
}
