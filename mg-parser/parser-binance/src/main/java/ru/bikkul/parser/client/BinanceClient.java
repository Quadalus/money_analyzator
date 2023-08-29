package ru.bikkul.parser.client;

import com.binance.api.client.BinanceApiRestClient;
import com.binance.api.client.domain.market.OrderBook;
import com.binance.api.client.domain.market.TickerPrice;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.bikkul.parser.service.CodeServiceImpl;
import ru.bikkul.parser.service.PriceServiceImpl;

import java.util.List;

@Slf4j
@Component
@EnableScheduling
@RequiredArgsConstructor
public class BinanceClient {
    private final CodeServiceImpl codeService;
    private final PriceServiceImpl priceService;
    private final BinanceApiRestClient restClient;

    @Scheduled(fixedRate = 60000)
    private void call() {
        List<TickerPrice> tickerPrices = restClient.getAllPrices();
        System.out.println(restClient.get24HrPriceStatistics("MATICUSDT"));
        System.out.println(restClient.get24HrPriceStatistics("MATICETH"));
        OrderBook maticeth = restClient.getOrderBook("MATICETH", 100);
        System.out.println(maticeth.getAsks());
        System.out.println("=============================================================");
        System.out.println("=============================================================");
        System.out.println("=============================================================");
        System.out.println("=============================================================");
        System.out.println("=============================================================");
        System.out.println("=============================================================");
        System.out.println("=============================================================");
        System.out.println("=============================================================");
        System.out.println("=============================================================");
        System.out.println(maticeth.getBids());
    }
}
