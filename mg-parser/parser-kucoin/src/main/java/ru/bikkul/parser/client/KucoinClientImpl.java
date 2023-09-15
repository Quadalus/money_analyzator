package ru.bikkul.parser.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import ru.bikkul.parser.domain.market.KlineFull;

@Service
public class KucoinClientImpl implements KucoinClient {
    @Value("${kucoin.api.key}")
    public String API_KEY;

    private final WebClient webClient;
    private static final String API_HEADER = "KC-API-KEY";
    private static final String API_TIMESTAMP = "KC-API-TIMESTAMP";
    private static final String API_SIGNATURE = "KC-API-SIGN";
    private static final String API_PASSPHRASE = "KC-API-PASSPHRASE";
    private static final String API_VERSION = "KC-API-KEY-VERSION";

    public KucoinClientImpl(@Value("${kucoin.api.base_url}") String url) {
        this.webClient = WebClient.create(url);
    }

    public KlineFull getKline(String symbol, String interval, Integer limit, long startTime, long endTime) {
        return webClient
                .get()
                .uri(uriBuilder -> uriBuilder.path("/api/v1/market/candles")
                        .queryParam("symbol", symbol)
                        .queryParam("type", interval)
                        .queryParam("startAt", startTime)
                        .queryParam("endAt", endTime)
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(KlineFull.class)
                .block();
    }
}
