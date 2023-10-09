package ru.bikkul.parser.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.bikkul.parser.client.BinanceParserClient;
import ru.bikkul.parser.domain.coin.CoinInfo;
import ru.bikkul.parser.domain.market.Candlestick;
import ru.bikkul.parser.domain.market.CandlestickInterval;
import ru.bikkul.parser.domain.market.OrderBook;
import ru.bikkul.parser.dto.KlineDto;
import ru.bikkul.parser.dto.KlineFullDataDto;
import ru.bikkul.parser.dto.OrderBookDto;
import ru.bikkul.parser.utils.mappers.KlineDtoMapper;
import ru.bikkul.parser.utils.mappers.KlineFullDataDtoMapper;
import ru.bikkul.parser.utils.mappers.OrderBookDtoMapper;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class BinanceParserServiceImpl implements BinanceParserService {
    private final BinanceParserClient binanceParseClient;

    @Override
    public Map<String, KlineFullDataDto> getKlineForFiveMin(Set<String> pairs) {
        Map<String, KlineFullDataDto> klines = new HashMap<>();
        String interval = CandlestickInterval.ONE_MINUTE.getIntervalId();
        Long start = Instant.now().minusSeconds(300).toEpochMilli();
        Long end = Instant.now().toEpochMilli();
        Integer limit = 5;

        for (String pair : pairs) {
            try {
                String formattedPair = formatPair(pair);
                List<KlineDto> klineForFiveMin = getKline(binanceParseClient
                        .getKlineForFiveMin(formattedPair, interval, start, end, limit));

                if (klineForFiveMin.isEmpty()) {
                    continue;
                }
                klines.put(pair, KlineFullDataDtoMapper.toKlineFullDataDto(klineForFiveMin));
            } catch (Exception e) {
                log.error("error from parse kline pair:{}, error: {}", pair, e.getMessage());
            }
        }
        return klines;
    }

    @Override
    public Map<String, OrderBookDto> getSpotData(Set<String> pairs) {
        Map<String, OrderBookDto> orderBook = new HashMap<>();
        Integer limit = 15;

        for (String pair : pairs) {
            try {
                String formattedPair = formatPair(pair);

                OrderBook pairOrderBook = binanceParseClient
                        .getPairOrderBook(formattedPair, limit);

                orderBook.put(pair, OrderBookDtoMapper.orderBookDto(pairOrderBook));
            } catch (Exception e) {
                log.error("error from parse order book:{}, error: {}", pair, e.getMessage());
            }
        }
        return orderBook;
    }

    @Override
    public List<CoinInfo> getCoinsInformation() {
        List<CoinInfo> coinsInformation = new ArrayList<>();
        Long timestamp = Instant.now().toEpochMilli();

        try {
            coinsInformation = binanceParseClient.getCoinsInformation(timestamp);
        } catch (Exception e) {
            log.error("error from getting coin info");
        }
        return coinsInformation;
    }

    private List<KlineDto> getKline(List<Candlestick> candlesticks) {
        return candlesticks.stream()
                .map(KlineDtoMapper::toKlineDto)
                .collect(Collectors.toList());
    }

    private String formatPair(String pair) {
        String[] coin = pair.split("-");
        return String.format("%s%s", coin[0], coin[1]).toUpperCase();
    }
}
