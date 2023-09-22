package ru.bikkul.parser.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class KrakenClientImpl implements KrakenClient {
    private final WebClient webClient;
    private final String KLINE_URI;

    public KrakenClientImpl(@Value("${bingx.api.base_url}") String url,
                            @Value("${bingx.api.kline_uri}") String klineUri) {
        this.webClient = WebClient.create(url);
        this.KLINE_URI = klineUri;
    }

    public String getKline(String symbol, String interval, long start) {
        return webClient
                .get()
                .uri(uriBuilder -> uriBuilder.path(KLINE_URI)
                        .queryParam("pair", symbol)
                        .queryParam("interval", interval)
                        .queryParam("since", start)
                        .build())
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

}
