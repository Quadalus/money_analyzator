package ru.bikkul.client.impl;

import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@Service
public class ParserClientImpl implements ParserClient {
    private  WebClient webClient;
    private final String PREFIX;
    private final String KLINE_URI;
    private final String ANALYZER_URL;
    private final String ANALYZER_KLINE_URI;
    private final String ANALYZER_ORDERS_URI;
    private final String ANALYZER_PORT;
    private final String COIN_URI;
    private final String ORDER_BOOK_URI;
    private final String BINANCE_URL;
    private final String BYBIT_URL;
    private final String MEXC_URL;
    private final String HUOBI_URL;
    private final String OKX_URL;

    public ParserClientImpl(@Value("${parser.api.base.analyzer_url}") String analyzerUrl,
                            @Value("${parser.api.base.prefix}") String prefix,
                            @Value("${parser.api.base.kline_uri}") String klineUri,
                            @Value("${parser.api.base.analyzer_kline_uri}") String analyzerKlinesUri,
                            @Value("${parser.api.base.analyzer_port}") String analyzerPort,
                            @Value("${parser.api.base.coin_uri}") String coinUri,
                            @Value("${parser.api.base.order_book_uri}") String orderBookUri,
                            @Value("${parser.api.base.analyzer_order_book_uri}") String analyzerOrdersUri,
                            @Value("${parser.api.base.binance_url}") String binance_url,
                            @Value("${parser.api.base.okx_url}") String okx_url,
                            @Value("${parser.api.base.bybit_url}") String bybit_url,
                            @Value("${parser.api.base.mexc_url}") String mexc_url,
                            @Value("${parser.api.base.huobi_url}") String huobi_url) {
        this.ANALYZER_URL = analyzerUrl;
        this.PREFIX = prefix;
        this.KLINE_URI = klineUri;
        this.ANALYZER_KLINE_URI = analyzerKlinesUri;
        this.ANALYZER_ORDERS_URI = analyzerOrdersUri;
        this.ANALYZER_PORT = analyzerPort;
        this.COIN_URI = coinUri;
        this.ORDER_BOOK_URI = orderBookUri;
        this.BINANCE_URL = binance_url;
        this.BYBIT_URL = bybit_url;
        this.MEXC_URL = mexc_url;
        this.HUOBI_URL = huobi_url;
        this.OKX_URL = okx_url;
    }

    @Override
    public Map<String, KlineDataDto> getKlineFromMarket(String port, Set<String> pairs) {
        this.webClient = WebClient.builder()
                .baseUrl(getUrl(port))
                .exchangeStrategies(
                        ExchangeStrategies.builder().codecs(
                                configurer -> configurer.defaultCodecs().maxInMemorySize(10000 * 1024)).build()
                )
                .build();
        String fullUri = PREFIX + KLINE_URI;
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .port(port)
                        .path(fullUri)
                        .queryParam("pairs", pairs)
                        .build())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, KlineDataDto>>() {
                })
                .block();
    }

    @Override
    public void sendKlinesDataToAnalyzer(Map<String, List<KlineDataDto>> klines) {
        this.webClient = WebClient.builder()
                .baseUrl(ANALYZER_URL)
                .exchangeStrategies(
                        ExchangeStrategies.builder().codecs(
                                configurer -> configurer.defaultCodecs().maxInMemorySize(10000 * 1024)).build()
                )
                .build();
        String fullUri = PREFIX + ANALYZER_KLINE_URI;
        webClient.post()
                .uri(uriBuilder -> uriBuilder
                        .port(ANALYZER_PORT)
                        .path(fullUri)
                        .build())
                .bodyValue(klines)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    @Override
    public void sendOrdersDataToAnalyzator(Map<String, List<OrderBookDto>> orderBooks) {
        this.webClient = WebClient.builder()
                .baseUrl(ANALYZER_URL)
                .exchangeStrategies(
                        ExchangeStrategies.builder().codecs(
                                configurer -> configurer.defaultCodecs().maxInMemorySize(10000 * 1024)).build()
                )
                .build();
        String fullUri = PREFIX + ANALYZER_ORDERS_URI;
        String block = webClient.post()
                .uri(uriBuilder -> uriBuilder
                        .port(ANALYZER_PORT)
                        .path(fullUri)
                        .build())
                .bodyValue(orderBooks)
                .retrieve()
                .bodyToMono(String.class)
                .block();
        log.info(block);
    }

    @Override
    public List<CoinInfoDto> getCoinInfoFromMarket(String port) {
        this.webClient = WebClient.builder()
                .baseUrl(getUrl(port))
                .exchangeStrategies(
                        ExchangeStrategies.builder().codecs(
                                configurer -> configurer.defaultCodecs().maxInMemorySize(10000 * 1024)).build()
                )
                .build();
        String fullUri = PREFIX + COIN_URI;
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .port(port)
                        .path(fullUri)
                        .build())
                .retrieve()
                .bodyToFlux(new ParameterizedTypeReference<CoinInfoDto>() {
                })
                .collectList()
                .block();
    }

    @Override
    public Map<String, OrderBookDto> getOrderBookFromMarket(String port, Set<String> pairs) {
        this.webClient = WebClient.builder()
                .baseUrl(getUrl(port))
                .exchangeStrategies(
                        ExchangeStrategies.builder().codecs(
                                configurer -> configurer.defaultCodecs().maxInMemorySize(10000 * 1024)).build()
                )
                .build();
        String fullUri = PREFIX + ORDER_BOOK_URI;
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .port(port)
                        .path(fullUri)
                        .queryParam("pairs", pairs)
                        .build())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, OrderBookDto>>() {
                })
                .block();
    }

    private String getUrl(String port) {
        return switch (Integer.parseInt(port)) {
            case 8081 -> BINANCE_URL;
            case 8082 -> HUOBI_URL;
            case 8083 -> OKX_URL;
            case 8084 -> MEXC_URL;
            case 8085 -> BYBIT_URL;
            case 8091 -> ANALYZER_URL;
            default -> "http://127.0.0.1";
        };
    }
}
