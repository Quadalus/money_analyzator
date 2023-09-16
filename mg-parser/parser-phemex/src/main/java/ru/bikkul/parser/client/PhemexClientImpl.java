package ru.bikkul.parser.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import ru.bikkul.parser.domain.market.KlineFull;

@Service
public class PhemexClientImpl implements PhemexClient {
    private final WebClient webClient;

    public PhemexClientImpl(@Value("${phemex.api.base_url}") String url) {
        this.webClient = WebClient.create(url);
    }

    public KlineFull getKline(String symbol, String interval, Integer limit) {
        return webClient
                .get()
                .uri(uriBuilder -> uriBuilder.path("/exchange/public/md/v2/kline/last")
                        .queryParam("symbol", symbol)
                        .queryParam("resolution", interval)
                        .queryParam("limit", limit)
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(KlineFull.class)
                .block();
    }
}
