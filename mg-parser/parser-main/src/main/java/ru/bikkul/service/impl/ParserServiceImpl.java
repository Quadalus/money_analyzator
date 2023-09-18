package ru.bikkul.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.bikkul.client.ParserClient;
import ru.bikkul.model.KlineData;
import ru.bikkul.service.ParserService;
import ru.bikkul.utils.Ports;

import java.util.*;

@Slf4j
@Service
@EnableScheduling
public class ParserServiceImpl implements ParserService {
    private final ParserClient parserClient;
    private final Set<String> pairs = new HashSet<>();
    private final Map<String, List<KlineData>> marketKlines;

    @Autowired
    public ParserServiceImpl(ParserClient parserClient) {
        this.parserClient = parserClient;
        this.marketKlines = new HashMap<>();
        pairs.add("ADA-USDT");
        pairs.add("TRX-USDT");
        pairs.add("PERP-USDT");
        pairs.add("DOT-USDT");
        pairs.add("SOL-USDT");
        pairs.add("BTC-USDT");
        pairs.add("WAVES-USTD");
        pairs.add("LTC-USDT");
        pairs.add("SFP-USDT");
    }

    @Override
    @Scheduled(fixedRate = 30000)
    public void initParser() {
        if (pairs.isEmpty()) {
            return;
        }
        for (Ports port : Ports.values()) {
            getKlineDataFromMarket(port);
        }
        sendKlinesDataToAnalyzer();
    }

    private void sendKlinesDataToAnalyzer() {
        try {
            parserClient.sendKlinesDataToAnalyzer(marketKlines);
            log.info("klines date send to analyzer");
            Thread.sleep(3000);
            clearKlinesData();
        } catch (Exception e) {
            log.error("error from sending klinesData to analyzer, exception msg:{}", e.getMessage());
        }
    }

    private void getKlineDataFromMarket(Ports port) {
        try {
            Map<String, KlineData> klineFromMarket = parserClient.getKlineFromMarket(port.getPort(), pairs);
            log.info("klines data from market has been got, klinesFromMarket:{}", klineFromMarket);
            addPairKlineData(klineFromMarket);
        } catch (Exception e) {
            log.error("error from getting kline data, exception msg:{}", e.getMessage());
        }
    }

    @Override
    public void setPairs(Set<String> pairs) {
        this.pairs.addAll(pairs);
    }

    @Override
    public void addPair(String pair) {
        this.pairs.add(pair);
    }

    @Override
    public void deletePairs(Set<String> pairs) {
        this.pairs.removeAll(pairs);
    }

    @Override
    public void deletePair(String pair) {
        this.pairs.remove(pair);
    }

    @Override
    public Map<String, List<KlineData>> getAllKlinesData() {
        return null;
    }

    @Override
    public void clearKlinesData() {
        marketKlines.clear();
        log.info("klines data has been clear");
    }

    @Override
    public void deleteAllPairs() {
        pairs.clear();
    }

    private void addPairKlineData(Map<String, KlineData> marketKline) {
        for (Map.Entry<String, KlineData> kline : marketKline.entrySet()) {
            KlineData klineData = kline.getValue();
            String pair = kline.getKey();

            if (!marketKlines.containsKey(pair)) {
                marketKlines.put(pair, new ArrayList<>());
            }
            marketKlines.get(pair).add(klineData);
        }
    }
}
