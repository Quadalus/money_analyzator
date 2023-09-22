package ru.bikkul.parser.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import ru.bikkul.parser.domain.market.Kline;

import java.util.List;

@Service
public class BitgetClientImpl implements BitgetClient {
    private final WebClient webClient;
    private final String KLINE_URI;

    public BitgetClientImpl(@Value("${bitget.api.base_url}") String url,
                            @Value("${bitget.api.kline_uri}") String klineUri) {
        this.webClient = WebClient.create(url);
        this.KLINE_URI = klineUri;
    }

    public void testConnection() {
        webClient
                .get()
                .uri("/api/v3/ping")
                .retrieve();
    }

    public List<Kline> getKline(String symbol, String interval, Integer limit, long startTime, long endTime) {
        return webClient
                .get()
                .uri(uriBuilder -> uriBuilder.path(KLINE_URI)
                        .queryParam("symbol", symbol)
                        .queryParam("granularity", interval)
                        .queryParam("limit", limit)
                        .queryParam("startTime", startTime)
                        .queryParam("endTime", endTime)
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<Kline>>() {
                })
                .block();

    }
}
