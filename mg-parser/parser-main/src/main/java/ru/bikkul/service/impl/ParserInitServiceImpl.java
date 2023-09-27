package ru.bikkul.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.bikkul.client.ParserClient;
import ru.bikkul.dto.KlineDataDto;
import ru.bikkul.service.ParserInitService;
import ru.bikkul.service.ParserMarketService;
import ru.bikkul.service.ParserPairService;
import ru.bikkul.utils.Markets;

import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class ParserInitServiceImpl implements ParserInitService {
    private final Map<String, List<KlineDataDto>> marketKlines = new HashMap<>();
    private final ParserMarketService parserMarketService;
    private final ParserPairService parserPairService;
    private final ParserClient parserClient;

    @Override
    @Scheduled(fixedDelay = 90000)
    public void initParser() {
        Set<String> pairs = parserPairService.getPairs();
        Set<Markets> trackingMarkets = parserMarketService.getTrackingMarkets();

        if (pairs.isEmpty()) {
            return;
        }
        for (Markets port : trackingMarkets) {
            getKlineDataFromMarket(port, pairs);
        }
        sendKlinesDataToAnalyzer();
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

    private void getKlineDataFromMarket(Markets port, Set<String> pairs) {
        try {
            Map<String, KlineDataDto> klineFromMarket = parserClient.getKlineFromMarket(port.getPort(), pairs);
            log.info("klines data from market has been got, klinesFromMarket:{}", klineFromMarket);
            addPairKlineData(klineFromMarket);
        } catch (Exception e) {
            log.error("error from getting kline data, exception msg:{}", e.getMessage());
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

    private void clearKlinesData() {
        marketKlines.clear();
    }
}
