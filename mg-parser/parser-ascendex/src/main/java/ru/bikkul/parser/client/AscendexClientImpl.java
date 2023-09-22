package ru.bikkul.parser.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class AscendexClientImpl implements AscendexClient {
    private final WebClient webClient;
    private final String KLINE_URI;

    public AscendexClientImpl(@Value("${ascendex.api.base_url}") String url,
                              @Value("${ascendex.api.kline_uri}") String klineUri) {
        this.webClient = WebClient.create(url);
        this.KLINE_URI = klineUri;
    }

    public String getKline(String symbol, String interval, long start, long end, int limit) {
        return webClient
                .get()
                .uri(uriBuilder -> uriBuilder.path(KLINE_URI)
                        .queryParam("symbol", symbol)
                        .queryParam("interval", interval)
                        .queryParam("from", start)
                        .queryParam("to", end)
                        .queryParam("n", limit)
                        .build())
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

}
