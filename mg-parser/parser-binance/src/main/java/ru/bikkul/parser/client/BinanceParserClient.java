package ru.bikkul.parser.client;

import com.binance.api.client.BinanceApiRestClient;
import com.binance.api.client.domain.market.Candlestick;
import com.binance.api.client.domain.market.CandlestickInterval;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class BinanceParserClient {
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

    public  List<Candlestick> getKlineForFiveMin(String pair) {
        List<Candlestick> candlesticks = new ArrayList<>();
        Long start = Instant.now().minusSeconds(360).toEpochMilli();
        Long end = Instant.now().toEpochMilli();
        Integer limit = 5;

        try {
            candlesticks = restClient.getCandlestickBars(pair, CandlestickInterval.ONE_MINUTE, limit, start, end);
            System.out.println(candlesticks);
        } catch (Exception e) {
            log.error("exception on getting kline, exception:{}", e.getMessage());
        }
        return candlesticks;
    }
}
