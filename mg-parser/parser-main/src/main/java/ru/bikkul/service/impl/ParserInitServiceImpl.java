package ru.bikkul.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.bikkul.client.ParserClient;
import ru.bikkul.dto.KlineDataDto;
import ru.bikkul.dto.OrderBookDto;
import ru.bikkul.service.ParserInitService;
import ru.bikkul.service.ParserMarketService;
import ru.bikkul.service.ParserPairService;
import ru.bikkul.utils.Markets;

import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class ParserInitServiceImpl implements ParserInitService {
    private Map<String, List<KlineDataDto>> marketKlines = new HashMap<>();
    private Map<String, List<OrderBookDto>> orderBooks = new HashMap<>();
    private final ParserMarketService parserMarketService;
    private final ParserPairService parserPairService;
    private final ParserClient parserClient;

    @Override
    @Scheduled(fixedDelay = 90000)
    public void initOrderBookParser() {
        /*Map<Markets, Set<String>> pairs = parserPairService.getPairs();
        Set<Markets> trackingMarkets = parserMarketService.getTrackingMarkets();

        if (pairs.isEmpty()) {
            return;
        }
        for (Markets marketName : trackingMarkets) {
            getKlineDataFromMarket(marketName, pairs.get(marketName));
        }
        sendKlinesDataToAnalyzer();*/
    }

    @Override
    @Scheduled(fixedDelay = 600000)
    public void initKlineParser() {
        Map<Markets, Set<String>> pairs = parserPairService.getPairs();
        Set<Markets> trackingMarkets = parserMarketService.getTrackingMarkets();

        if (pairs.isEmpty()) {
            return;
        }
        for (Markets marketName : trackingMarkets) {
            getOrderBookDataFromMarket(marketName, pairs.get(marketName));
        }
        sendOrderBookDataToAnalyzer();
    }

    private void sendKlinesDataToAnalyzer() {
        try {
            parserClient.sendKlinesDataToAnalyzer(marketKlines);
            log.info("klines date send to analyzer");
            clearKlinesData();
        } catch (Exception e) {
            log.error("error from sending klinesData to analyzer, exception msg:{}", e.getMessage());
        }
    }

    private void sendOrderBookDataToAnalyzer() {
        try {
            parserClient.sendOrdersDataToAnalyzator(orderBooks);
            log.info("order book data send to analyzer");
            clearOrderBookData();
        } catch (Exception e) {
            log.error("error from sending klinesData to analyzer, exception msg:{}", e.getMessage());
        }
    }

    private void getKlineDataFromMarket(Markets marketName, Set<String> pairs) {
        try {
            Map<String, KlineDataDto> klineFromMarket = parserClient.getKlineFromMarket(marketName.getPort(), pairs);
            log.info("klines data from market has been got, klinesFromMarket:{}", klineFromMarket);
            addPairKlineData(klineFromMarket);
        } catch (Exception e) {
            log.error("error from getting order data, exception msg:{}", e.getMessage());
        }
    }

    private void getOrderBookDataFromMarket(Markets marketName, Set<String> pairs) {
        try {
            Map<String, OrderBookDto> orderBookFromMarket = parserClient.getOrderBookFromMarket(marketName.getPort(), pairs);
            log.info("order book from market has been got, orders from market size:{}", orderBookFromMarket.size());
            addPairOrderBookData(orderBookFromMarket);
        } catch (Exception e) {
            log.error("error from getting order book, exception msg:{}", e.getMessage());
        }
    }

    private void addPairKlineData(Map<String, KlineDataDto> marketKline) {
        for (Map.Entry<String, KlineDataDto> kline : marketKline.entrySet()) {
            KlineDataDto klineData = kline.getValue();
            String pair = kline.getKey();

            if (!marketKlines.containsKey(pair)) {
                marketKlines.put(pair, new ArrayList<>());
            }
            marketKlines.get(pair).add(klineData);
        }
    }

    private void addPairOrderBookData(Map<String, OrderBookDto> orderBook) {
        for (Map.Entry<String, OrderBookDto> order : orderBook.entrySet()) {
            OrderBookDto orderBookDto = order.getValue();
            String pair = order.getKey();

            if (!orderBooks.containsKey(pair)) {
                orderBooks.put(pair, new ArrayList<>());
            }
            orderBooks.get(pair).add(orderBookDto);
        }
    }

    private void clearKlinesData() {
        marketKlines.clear();
    }

    private void clearOrderBookData() {
        orderBooks.clear();
    }
}
