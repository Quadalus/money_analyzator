package ru.bikkul.parser.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import ru.bikkul.parser.domain.market.KlineFull;

@Service
public class HuobiClientImpl implements HuobiClient {
    private final WebClient webClient;

    public HuobiClientImpl(@Value("${huobi.api.base_url}") String url) {
        this.webClient = WebClient.create(url);
    }

    public KlineFull getKline(String symbol, String interval, Integer limit) {
        return webClient
                .get()
                .uri(uriBuilder -> uriBuilder.path("/market/history/kline")
                        .queryParam("symbol", symbol)
                        .queryParam("period", interval)
                        .queryParam("size", limit)
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(KlineFull.class)
                .block();
    }
}
