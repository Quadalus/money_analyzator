package ru.bikkul.parser.client;

import com.binance.api.client.BinanceApiRestClient;
import com.binance.api.client.domain.market.OrderBook;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.bikkul.parser.service.BinanceOrderBookService;

import static ru.bikkul.parser.utils.Pairs.*;

@Slf4j
@Component
@EnableScheduling
@RequiredArgsConstructor
public class BinanceParseClient {
    private static final long RATE_DELAY = 60000;
    private final BinanceOrderBookService binanceOrderBookService;
    private final BinanceApiRestClient restClient;

    @Scheduled(fixedRate = RATE_DELAY)
    private void getEthUsdtOrders() {
        String pair = ETH_USDT;
        OrderBook orderBook = restClient.getOrderBook(pair, 100);
        binanceOrderBookService.parse(orderBook, pair);
    }

    @Scheduled(fixedRate = RATE_DELAY)
    private void getMaticEthOrders() {
        String pair = MATIC_ETH;
        OrderBook orderBook = restClient.getOrderBook(pair, 100);
        binanceOrderBookService.parse(orderBook, pair);
    }

    @Scheduled(fixedRate = RATE_DELAY)
    private void getMaticUsdtOrders() {
        String pair = MATIC_USDT;
        OrderBook orderBook = restClient.getOrderBook(pair, 100);
        binanceOrderBookService.parse(orderBook, pair);
    }
}
