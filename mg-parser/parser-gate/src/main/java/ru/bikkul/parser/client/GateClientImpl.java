package ru.bikkul.parser.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import ru.bikkul.parser.domain.market.Kline;

import java.util.List;

@Service
public class GateClientImpl implements GateClient {
    @Value("${gate.api.kline_uri}")
    public String KLINE_URI;

    private final WebClient webClient;

    public GateClientImpl(@Value("${gate.api.base_url}") String url) {
        this.webClient = WebClient.create(url);
    }

    public List<Kline> getKline(String symbol, String interval, Integer limit, long startTime, long endTime) {
        return webClient
                .get()
                .uri(uriBuilder -> uriBuilder.path(KLINE_URI)
                        .queryParam("currency_pair", symbol)
                        .queryParam("interval", interval)
                        .queryParam("limit", limit)
                        .queryParam("from", startTime)
                        .queryParam("to", endTime)
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<Kline>>() {
                })
                .block();

    }
}
