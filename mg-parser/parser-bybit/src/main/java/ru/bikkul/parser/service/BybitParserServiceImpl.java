package ru.bikkul.parser.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.bikkul.parser.client.BybitClientImpl;
import ru.bikkul.parser.domain.coin.CoinInfo;
import ru.bikkul.parser.domain.market.KlineFull;
import ru.bikkul.parser.domain.market.KlineInterval;
import ru.bikkul.parser.domain.market.OrderBookDepth;
import ru.bikkul.parser.dto.CoinInfoDto;
import ru.bikkul.parser.dto.KlineDto;
import ru.bikkul.parser.dto.KlineFullDataDTO;
import ru.bikkul.parser.dto.OrderBookDto;
import ru.bikkul.parser.utils.mapper.CoinInfoDtoMapper;
import ru.bikkul.parser.utils.mapper.KlineDtoMapper;
import ru.bikkul.parser.utils.mapper.KlineFullDataDtoMapper;
import ru.bikkul.parser.utils.mapper.OrderBookDtoMapper;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class
BybitParserServiceImpl implements BybitParserService {
    private final BybitClientImpl bybitClient;

    @Override
    public Map<String, KlineFullDataDTO> getKlineForFiveMin(Set<String> pairs) {
        Map<String, KlineFullDataDTO> klines = new HashMap<>();
        long start = Instant.now().minusSeconds(300).toEpochMilli();
        long end = Instant.now().toEpochMilli();
        String interval = KlineInterval.ONE_MINUTE.getIntervalId();
        Integer limit = 5;

        for (String pair : pairs) {
            try {
                String rightFormattedPair = formatPair(pair);
                List<KlineDto> klinesDto = getKline(bybitClient.getKline(rightFormattedPair, interval, limit, start, end));
                if (klinesDto.isEmpty()) {
                    continue;
                }
                klines.put(pair, KlineFullDataDtoMapper.toKlineFullDataDto(klinesDto));
            } catch (Exception e) {
                log.error("error from parse kline pair:{}, error: {}", pair, e.getMessage());
            }
        }
        return klines;
    }

    @Override
    public List<CoinInfoDto> getCoinsInformation() {
        CoinInfo coinsInformation = new CoinInfo();
        try {
            coinsInformation = bybitClient.getCoinsInformation();
        } catch (Exception e) {
            log.error("error from getting coin info, exception msg:{}", e.getMessage());
        }

        return CoinInfoDtoMapper.toCoinInfoDto(coinsInformation);
    }

    @Override
    public Map<String, OrderBookDto> getSpotData(Set<String> pairs) {
        Map<String, OrderBookDto> orderBook = new HashMap<>();
        Integer limit = 20;

        for (String pair : pairs) {
            try {
                String formattedPair = formatPair(pair);

                OrderBookDepth pairOrderBook = bybitClient
                        .getPairOrderBook(formattedPair, limit);
                checkResult(formattedPair, pairOrderBook);
                orderBook.put(pair, OrderBookDtoMapper.orderBookDto(pairOrderBook));
            } catch (Exception e) {
                log.error("error from parse order book:{}, error: {}", pair, e.getMessage());
            }
        }
        return orderBook;
    }

    private static void checkResult(String formattedPair, OrderBookDepth pairOrderBook) {
        if (pairOrderBook.getResult().getA().isEmpty()) {
            throw new RuntimeException(String.format("empty result from symbol pair:%s", formattedPair));
        }
    }

    private List<KlineDto> getKline(KlineFull fullKline) {
        return fullKline.getResult().getList().stream()
                .map(KlineDtoMapper::toKlineDto)
                .collect(Collectors.toList());
    }

    private String formatPair(String pair) {
        String[] coin = pair.split("-");
        return String.format("%s%s", coin[0], coin[1]).toUpperCase();
    }
}
