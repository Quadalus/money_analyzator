package ru.bikkul.parser.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import ru.bikkul.parser.domain.market.KlineFull;

@Service
public class LbankClientImpl implements LbankClient {
    @Value("${lbank.api.kline_uri}")
    public String KLINE_URI;

    private final WebClient webClient;

    public LbankClientImpl(@Value("${lbank.api.base_url}") String url) {
        this.webClient = WebClient.create(url);
    }

    public void testConnection() {
        webClient
                .get()
                .uri("test")
                .retrieve();
    }

    public KlineFull getKline(String symbol, String interval, Integer limit, long time) {
        return webClient
                .get()
                .uri(uriBuilder -> uriBuilder.path(KLINE_URI)
                        .queryParam("symbol", symbol)
                        .queryParam("type", interval)
                        .queryParam("size", limit)
                        .queryParam("time", time)
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(KlineFull.class)
                .block();

    }
}
