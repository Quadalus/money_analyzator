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
    private static final long RATE_DELAY = 300000;
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

    @Scheduled(fixedRate = RATE_DELAY)
    private void get24hStatistics() {
        String ethUsdt = ETH_USDT;
        String priceChangePercentETH_USDT = restClient.get24HrPriceStatistics(ethUsdt).getPriceChangePercent();
        String maticUsdt = MATIC_USDT;
        String priceChangePercentMATIC_USDT = restClient.get24HrPriceStatistics(maticUsdt).getPriceChangePercent();
        String maticEth = MATIC_ETH;
        String priceChangePercentMATIC_ETH = restClient.get24HrPriceStatistics(maticEth).getPriceChangePercent();

        binanceOrderBookService.getPriceChangePercent(priceChangePercentETH_USDT, ethUsdt);
        binanceOrderBookService.getPriceChangePercent(priceChangePercentMATIC_USDT, maticUsdt);
        binanceOrderBookService.getPriceChangePercent(priceChangePercentMATIC_ETH, maticEth);
    }
}
