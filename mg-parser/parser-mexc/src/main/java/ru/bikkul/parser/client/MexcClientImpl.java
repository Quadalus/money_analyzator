package ru.bikkul.parser.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import ru.bikkul.parser.domain.coin.CoinInfo;
import ru.bikkul.parser.domain.market.Kline;
import ru.bikkul.parser.domain.market.OrderBook;
import ru.bikkul.parser.utils.MexcApiProvider;
import ru.bikkul.parser.utils.SignatureGenerator;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Service
public class MexcClientImpl implements MexcClient {
    private final WebClient webClient;
    private final String KLINE_URI;
    private final String COIN_INFO_URI;
    private final String ORDER_BOOK_URI;
    private final MexcApiProvider provider;
    private final String API_HEADER_NAME = "X-MEXC-APIKEY";

    public MexcClientImpl(@Value("${mexc.api.base_url}") String url,
                          @Value("${mexc.api.kline_uri}") String klineUri,
                          @Value("${mexc.api.coin_info_uri}") String coinInfoUri,
                          MexcApiProvider mexcApiProvider,
                          @Value("${mexc.api.order_book_uri}") String orderBookUri) {
        this.webClient = WebClient.create(url);
        this.KLINE_URI = klineUri;
        this.provider = mexcApiProvider;
        this.COIN_INFO_URI = coinInfoUri;
        this.ORDER_BOOK_URI = orderBookUri;
    }

    public void testConnection() {
        webClient
                .get()
                .uri("/api/v3/ping")
                .retrieve();
    }

    public List<Kline> getKline(String symbol, String interval, Integer limit, long startTime, long endTime) {
        return webClient
                .get()
                .uri(uriBuilder -> uriBuilder.path(KLINE_URI)
                        .queryParam("symbol", symbol)
                        .queryParam("interval", interval)
                        .queryParam("limit", limit)
                        .queryParam("startTime", startTime)
                        .queryParam("endTime", endTime)
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<Kline>>() {
                })
                .block();

    }

    @Override
    public List<CoinInfo> getCoinsInformation() {
        Map<String, String> parameters = new TreeMap<>();
        Long timestamp = Instant.now().toEpochMilli();
        parameters.put("timestamp", timestamp.toString());
        String query = SignatureGenerator.getMessageToDigest(parameters);
        String signature = SignatureGenerator.generateHmac256(query, provider.getApiSecret());

        return webClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path(COIN_INFO_URI)
                        .queryParam("timestamp", timestamp)
                        .queryParam("signature", signature)
                        .build())
                .header(API_HEADER_NAME, provider.getApiKey())
                .retrieve()
                .bodyToFlux(new ParameterizedTypeReference<CoinInfo>() {
                })
                .collectList()
                .block();
    }

    @Override
    public OrderBook getPairOrderBook(String pair, Integer limit) {
        return webClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path(ORDER_BOOK_URI)
                        .queryParam("symbol", pair)
                        .queryParam("limit", limit)
                        .build())
                .retrieve()
                .bodyToMono(OrderBook.class)
                .block();
    }
}
