package ru.bikkul.parser.client;

import com.binance.api.client.BinanceApiRestClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import static ru.bikkul.parser.utils.Pairs.*;

@Slf4j
@Component
@EnableScheduling
@RequiredArgsConstructor
public class BinanceParseClient {
    private static final long RATE_DELAY = 60000;

    private final BinanceApiRestClient restClient;


    @Scheduled(fixedRate = RATE_DELAY)
    private void getEthUsdtOrders() {
        restClient.getOrderBook(ETH_USDT, 100);
    }

    @Scheduled(fixedRate = RATE_DELAY)
    private void getMaticEthOrders() {
        restClient.getOrderBook(MATIC_ETH, 100);
    }

    @Scheduled(fixedRate = RATE_DELAY)
    private void getMaticUsdtOrders() {
        restClient.getOrderBook(MATIC_USDT, 100);
    }
}
