package ru.bikkul.parser.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import ru.bikkul.parser.domain.market.KlineFull;

@Service
public class ProbitClientImpl implements ProbitClient {
    private final WebClient webClient;
    private final String KLINE_URI;

    public ProbitClientImpl(@Value("${probit.api.base_url}") String url,
                            @Value("${probit.api.kline_uri}") String klineUri) {
        this.webClient = WebClient.create(url);
        this.KLINE_URI = klineUri;
    }

    public KlineFull getKline(String symbol, String interval, Integer limit, String startTime, String endTime, String sort) {
        return webClient
                .get()
                .uri(uriBuilder -> uriBuilder.path(KLINE_URI)
                        .queryParam("market_ids", symbol)
                        .queryParam("interval", interval)
                        .queryParam("start_time", startTime)
                        .queryParam("end_time", endTime)
                        .queryParam("sort", sort)
                        .queryParam("limit", limit)
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(KlineFull.class)
                .block();
    }
}
