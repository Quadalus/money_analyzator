package ru.bikkul.parser.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import ru.bikkul.parser.domain.market.Kline;

import java.util.List;

@Service
public class MexcClientImpl {
    @Value("${mexc.api.key}")
    public String API_KEY;

    private final WebClient webClient;
    private static final String API_HEADER = "X-MEXC-APIKEY";
    private static final String CONTENT_TYPE = "Content-Type";

    public MexcClientImpl(@Value("${mexc.api.base_url}") String url) {
        this.webClient = WebClient.create(url);
    }

    public void testConnection() {
        webClient
                .get()
                .uri("/api/v3/ping")
                .retrieve();
    }

    public List<Kline> getKline(String symbol, String interval, Integer limit, long startTime, long endTime) {
        ObjectMapper objectMapper = new ObjectMapper();
        return webClient
                .get()
                .uri(uriBuilder -> uriBuilder.path("/api/v3/klines")
                        .queryParam("symbol", symbol)
                        .queryParam("interval", interval)
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
