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
    private final String ANALYZER_URI;
    private final String ANALYZER_PORT;

    public ParserClientImpl(@Value("${parser.api.base.url}") String url,
                            @Value("${parser.api.base.prefix}") String prefix,
                            @Value("${parser.api.base.kline_uri}") String klineUri,
                            @Value("${parser.api.base.analyzer_uri}") String analyzerUri,
                            @Value("${parser.api.base.analyzer_port}") String analyzerPort) {
        this.webClient = WebClient.create(url);
        this.PREFIX = prefix;
        this.KLINE_URI = klineUri;
        this.ANALYZER_URI = analyzerUri;
        this.ANALYZER_PORT = analyzerPort;
    }

    @Override
    public Map<String, KlineData> getKlineFromMarket(String port, Set<String> pairs) {
        String fullUri = PREFIX + KLINE_URI;

        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .port(port)
                        .path(fullUri)
                        .queryParam("pairs", pairs)
                        .build())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, KlineData>>() {
                })
                .block();
    }

    @Override
    public void sendKlinesDataToAnalyzer(Map<String, List<KlineData>> klines) {
        String fullUri = PREFIX + ANALYZER_URI;
        webClient.post()
                .uri(uriBuilder -> uriBuilder
                        .port(ANALYZER_PORT)
                        .path(fullUri)
                        .build())
                .bodyValue(klines)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, List<KlineData>>>() {
                })
                .block();
    }
}
