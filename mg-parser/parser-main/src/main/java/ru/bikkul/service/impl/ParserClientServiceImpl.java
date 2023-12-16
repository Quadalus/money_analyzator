package ru.bikkul.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;
import ru.bikkul.client.ParserClient;
import ru.bikkul.dto.coin.CoinInfoDto;
import ru.bikkul.service.ParserClientService;
import ru.bikkul.service.ParserMarketService;
import ru.bikkul.service.ParserPairService;
import ru.bikkul.utils.Markets;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
@EnableScheduling
@RequiredArgsConstructor
public class ParserClientServiceImpl implements ParserClientService {
    private final ParserMarketService parserMarketService;
    private final ParserPairService parserPairService;
    private final ParserClient parserClient;

    @Override
    public List<CoinInfoDto> getAllCoinInfo() {
        Set<Markets> trackingMarkets = parserMarketService.getTrackingMarkets();
        List<CoinInfoDto> coins = new ArrayList<>();

        for (Markets port : trackingMarkets) {
            List<CoinInfoDto> coinInfoFromMarket = getCoinInfoFromMarket(port);
            coins.addAll(coinInfoFromMarket);
        }
        return coins;
    }


    private List<CoinInfoDto> getCoinInfoFromMarket(Markets port) {
        List<CoinInfoDto> coinInfoMarket = new ArrayList<>();
        try {
            coinInfoMarket = parserClient.getCoinInfoFromMarket(port.getPort());
        } catch (Exception e) {
            log.error("error from getting kline data, exception msg:{}", e.getMessage());
        }
        return coinInfoMarket;
    }
}
