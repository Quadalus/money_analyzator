package ru.bikkul.parser.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import ru.bikkul.parser.domain.market.KlineFull;

@Service
public class KucoinClientImpl implements KucoinClient {
    private final WebClient webClient;
    private final String KLINE_URI;

    public KucoinClientImpl(@Value("${kucoin.api.base_url}") String url,
                            @Value("${kucoin.api.kline_uri}") String klineUri) {
        this.webClient = WebClient.create(url);
        this.KLINE_URI = klineUri;
    }

    public KlineFull getKline(String symbol, String interval, Integer limit, long startTime, long endTime) {
        return webClient
                .get()
                .uri(uriBuilder -> uriBuilder.path(KLINE_URI)
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
