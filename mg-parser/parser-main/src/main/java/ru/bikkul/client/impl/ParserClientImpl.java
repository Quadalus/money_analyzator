package ru.bikkul.client.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import ru.bikkul.client.ParserClient;
import ru.bikkul.model.KlineData;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class ParserClientImpl implements ParserClient {
    private final WebClient webClient;
    private final String PREFIX;
    private final String KLINE_URI;

    public ParserClientImpl(@Value("${parsers.api.base.url}") String url,
                            @Value("${parsers.api.base.prefix}") String prefix,
                            @Value("${parsers.api.base.kline_uri}") String klineUri) {
        this.webClient = WebClient.create(url);
        this.PREFIX = prefix;
        this.KLINE_URI = klineUri;
    }

    @Override
    public Map<String, List<KlineData>> getKlineFromMarket(String port, Set<String> pairs) {
        String fullUri = port + PREFIX + KLINE_URI;

        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path(fullUri)
                        .queryParam("pairs", pairs)
                        .build())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, List<KlineData>>>() {
                })
                .block();
    }
}
