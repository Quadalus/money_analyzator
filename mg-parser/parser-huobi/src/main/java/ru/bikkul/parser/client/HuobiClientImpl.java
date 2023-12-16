package ru.bikkul.parser.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import ru.bikkul.parser.domain.coin.CoinInfo;
import ru.bikkul.parser.domain.market.KlineFull;
import ru.bikkul.parser.domain.market.OrderBookDepth;

@Slf4j
@Service
public class HuobiClientImpl implements HuobiClient {
    private final WebClient webClient;
    private final String KLINE_URI;
    private final String COIN_INFO_URI;
    private final String ORDER_BOOK_URI;

    public HuobiClientImpl(@Value("${huobi.api.base_url}") String url,
                           @Value("${huobi.api.kline_uri}") String klineUri,
                           @Value("${huobi.api.coin_info_uri}") String coinInfoUri,
                           @Value("${huobi.api.order_book_uri}") String orderBookUri) {
        this.webClient = WebClient.builder()
                .baseUrl(url)
                .exchangeStrategies(
                        ExchangeStrategies.builder().codecs(
                                configurer -> configurer.defaultCodecs().maxInMemorySize(1000 * 1024)).build()
                )
                .build();
        this.KLINE_URI = klineUri;
        this.COIN_INFO_URI = coinInfoUri;
        this.ORDER_BOOK_URI = orderBookUri;
    }

    public KlineFull getKline(String symbol, String interval, Integer limit) {
        return webClient
                .get()
                .uri(uriBuilder -> uriBuilder.path(KLINE_URI)
                        .queryParam("symbol", symbol)
                        .queryParam("period", interval)
                        .queryParam("size", limit)
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(KlineFull.class)
                .block();
    }

    @Override
    public CoinInfo getCoinsInformation() {
        return webClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path(COIN_INFO_URI)
                        .build())
                .retrieve()
                .bodyToMono(CoinInfo.class)
                .block();
    }

    @Override
    public OrderBookDepth getPairOrderBook(String pair, Integer limit) {
        String type = "step0";

        return webClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path(ORDER_BOOK_URI)
                        .queryParam("symbol", pair)
//LIMIT ON DEFAULT 150
//                        .queryParam("depth", limit)
                        .queryParam("type", type)
                        .build())
                .retrieve()
                .bodyToMono(OrderBookDepth.class)
                .block();
    }
}
