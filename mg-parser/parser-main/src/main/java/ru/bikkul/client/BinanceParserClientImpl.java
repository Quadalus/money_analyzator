package ru.bikkul.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import ru.bikkul.model.KlineData;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Service("binance")
public class BinanceParserClientImpl implements ParserClient {
    private final WebClient webClient;

    public BinanceParserClientImpl(@Value("${parsers.api.base.url}") String url) {
        this.webClient = WebClient.builder()
                .baseUrl(url)
                .build();
    }

    @Override
    public Map<String, List<KlineData>> getKlineFromMarket(String port, Set<String> pairs) {
        return webClient.get()
                .uri("")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, List<KlineData>>>() {})
                .block();
    }
}
