package ru.bikkul.parser.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import ru.bikkul.parser.domain.market.KlineFull;

@Service
public class BybitClientImpl implements BybitClient {
    @Value("${bybit.api.key}")
    public String API_KEY;

    private final WebClient webClient;
    private static final String API_HEADER = "X-BAPI-API-KEY";
    private static final String API_TIMESTAMP = "X-BAPI-TIMESTAMP";
    private static final String API_SIGNATURE = "X-BAPI-SIGN";

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
