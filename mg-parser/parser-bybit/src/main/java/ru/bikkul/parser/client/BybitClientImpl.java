package ru.bikkul.parser.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import ru.bikkul.parser.domain.market.KlineFull;

@Service
public class BybitClientImpl implements BybitClient {
    private final WebClient webClient;

    public BybitClientImpl(@Value("${bybit.api.base_url}") String url) {
        this.webClient = WebClient.create(url);
    }

    public KlineFull getKline(String symbol, String interval, Integer limit, long startTime, long endTime) {
        return webClient
                .get()
                .uri(uriBuilder -> uriBuilder.path("/v5/market/kline")
                        .queryParam("category", "spot")
                        .queryParam("symbol", symbol)
                        .queryParam("interval", interval)
                        .queryParam("limit", limit)
                        .queryParam("start", startTime)
                        .queryParam("end", endTime)
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(KlineFull.class)
                .block();
    }
}
