package ru.bikkul.client.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import ru.bikkul.client.ParserClient;
import ru.bikkul.dto.KlineDataDto;
import ru.bikkul.dto.OrderBookDto;
import ru.bikkul.dto.coin.CoinInfoDto;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class ParserClientImpl implements ParserClient {
    private final WebClient webClient;
    private final String PREFIX;
    private final String KLINE_URI;
    private final String ANALYZER_HOST;
    private final String ANALYZER_KLINE_URI;
    private final String ANALYZER_ORDERS_URI;
    private final String ANALYZER_PORT;
    private final String COIN_URI;
    private final String ORDER_BOOK_URI;
    private final String BINANCE_HOST;
    private final String BYBIT_HOST;
    private final String MEXC_HOST;
    private final String HUOBI_HOST;
    private final String OKX_HOST;

    {
        this.webClient = WebClient.builder()
                .exchangeStrategies(
                        ExchangeStrategies.builder()
                                .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(15000 * 1024))
                                .build()
                )
                .build();
    }

    public ParserClientImpl(@Value("${parser.api.base.analyzer_host}") String analyzerHost,
                            @Value("${parser.api.base.prefix}") String prefix,
                            @Value("${parser.api.base.kline_uri}") String klineUri,
                            @Value("${parser.api.base.analyzer_kline_uri}") String analyzerKlinesUri,
                            @Value("${parser.api.base.analyzer_port}") String analyzerPort,
                            @Value("${parser.api.base.coin_uri}") String coinUri,
                            @Value("${parser.api.base.order_book_uri}") String orderBookUri,
                            @Value("${parser.api.base.analyzer_order_book_uri}") String analyzerOrdersUri,
                            @Value("${parser.api.base.binance_host}") String binanceHost,
                            @Value("${parser.api.base.okx_host}") String okxHost,
                            @Value("${parser.api.base.bybit_host}") String bybitHost,
                            @Value("${parser.api.base.mexc_host}") String mexcHost,
                            @Value("${parser.api.base.huobi_host}") String huobiHost) {
        this.ANALYZER_HOST = analyzerHost;
        this.PREFIX = prefix;
        this.KLINE_URI = klineUri;
        this.ANALYZER_KLINE_URI = analyzerKlinesUri;
        this.ANALYZER_ORDERS_URI = analyzerOrdersUri;
        this.ANALYZER_PORT = analyzerPort;
        this.COIN_URI = coinUri;
        this.ORDER_BOOK_URI = orderBookUri;
        this.BINANCE_HOST = binanceHost;
        this.BYBIT_HOST = bybitHost;
        this.MEXC_HOST = mexcHost;
        this.HUOBI_HOST = huobiHost;
        this.OKX_HOST = okxHost;
    }

    @Override
    public Map<String, KlineDataDto> getKlineFromMarket(String port, Set<String> pairs) {
        String uriPath = PREFIX + KLINE_URI;
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .scheme("http")
                        .host(getHost(port))
                        .port(port)
                        .path(uriPath)
                        .queryParam("pairs", pairs)
                        .build())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, KlineDataDto>>() {
                })
                .block();
    }

    @Override
    public void sendKlinesDataToAnalyzer(Map<String, List<KlineDataDto>> klines) {
        String uriPath = PREFIX + ANALYZER_KLINE_URI;
        webClient.post()
                .uri(uriBuilder -> uriBuilder
                        .scheme("http")
                        .host(ANALYZER_HOST)
                        .port(ANALYZER_PORT)
                        .path(uriPath)
                        .build())
                .bodyValue(klines)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    @Override
    public void sendOrdersDataToAnalyzator(Map<String, List<OrderBookDto>> orderBooks) {
        String uriPath = PREFIX + ANALYZER_ORDERS_URI;
        webClient.post()
                .uri(uriBuilder -> uriBuilder
                        .scheme("http")
                        .host(ANALYZER_HOST)
                        .port(ANALYZER_PORT)
                        .path(uriPath)
                        .build())
                .bodyValue(orderBooks)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    @Override
    public List<CoinInfoDto> getCoinInfoFromMarket(String port) {
        String uriPath = PREFIX + COIN_URI;
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .scheme("http")
                        .host(getHost(port))
                        .port(port)
                        .path(uriPath)
                        .build())
                .retrieve()
                .bodyToFlux(new ParameterizedTypeReference<CoinInfoDto>() {
                })
                .collectList()
                .block();
    }

    @Override
    public Map<String, OrderBookDto> getOrderBookFromMarket(String port, Set<String> pairs) {
        String uriPath = PREFIX + ORDER_BOOK_URI;
        return webClient.get()
                .uri(uriBuilder -> {
                    String host = getHost(port);
                    return uriBuilder
                            .scheme("http")
                            .host(host)
                            .port(port)
                            .path(uriPath)
                            .queryParam("pairs", pairs)
                            .build();
                })
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, OrderBookDto>>() {
                })
                .block();
    }

    private String getHost(String port) {
        return switch (Integer.parseInt(port)) {
            case 8081 -> BINANCE_HOST;
            case 8082 -> HUOBI_HOST;
            case 8083 -> OKX_HOST;
            case 8084 -> MEXC_HOST;
            case 8085 -> BYBIT_HOST;
            case 8091 -> ANALYZER_HOST;
            default -> "localhost";
        };
    }
}
