package ru.bikkul.client;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import ru.bikkul.model.KlineData;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

@Service("binance")
public class BinanceParserClientImpl implements ParserClient {
    private final WebClient webClient;

    public BinanceParserClientImpl(String url) {
        this.webClient = WebClient.builder()
                .baseUrl(url)
                .build();
    }

    @Override
    public HashMap<String, List<KlineData>> getKlineFromMarket(Set<String> pairs) {
        /*webClient.get()
                .uri("")
                .retrieve()
                .bodyToFlux(new ParameterizedTypeReference<Object>() {
                })
                .collectMap();*/
        return null;
    }
}
