package ru.bikkul.parser.client;

import com.binance.api.client.BinanceApiRestClient;
import com.binance.api.client.domain.market.Candlestick;
import com.binance.api.client.domain.market.CandlestickInterval;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;
import ru.bikkul.parser.service.BinanceParserService;

import java.time.Instant;
import java.util.List;

import static ru.bikkul.parser.utils.Pairs.*;

@Slf4j
@Component
@EnableScheduling
@RequiredArgsConstructor
public class BinanceParserClient {
    private static final long RATE_DELAY = 300000;
    private final BinanceParserService binanceOrderBookService;
    private final BinanceApiRestClient restClient;

    /*public void getMaticUsdtOrders() {
        String pair = MATIC_USDT;
        OrderBook orderBook = restClient.getOrderBook(pair, 100);
        binanceOrderBookService.parse(orderBook, pair);
    }

    public void getEthUsdtOrders() {
        String pair = ETH_USDT;
        OrderBook orderBook = restClient.getOrderBook(pair, 100);
        binanceOrderBookService.parse(orderBook, pair);
    }

    private void getMaticEthOrders() {
        String pair = MATIC_ETH;
        OrderBook orderBook = restClient.getOrderBook(pair, 100);
        binanceOrderBookService.parse(orderBook, pair);
    }*/

    public void getCandlesForFiveMin() {
        Long start = Instant.now().minusSeconds(300).toEpochMilli();
        Long end = Instant.now().toEpochMilli();
        Integer limit = 15;
        List<String> pairs = List.of(MATIC_ETH, ETH_USDT, MATIC_USDT);
        for (String pair : pairs) {
            List<Candlestick> candlestickBars = restClient.getCandlestickBars(pair, CandlestickInterval.ONE_MINUTE, limit, start, end);
        }
    }
}
