package ru.bikkul.parser.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.bikkul.parser.client.HuobiClientImpl;
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

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class HuobiParserServiceImpl implements HuobiParserService {
    private final HuobiClientImpl huobiClient;

    @Override
    public Map<String, KlineFullDataDTO> getKlineForFiveMin(Set<String> pairs) {
        Map<String, KlineFullDataDTO> klines = new HashMap<>();
        String interval = KlineInterval.ONE_MINUTE.getIntervalId();
        Integer limit = 5;

        for (String pair : pairs) {
            try {
                String rightFormattedPair = formatPair(pair);
                KlineFull kline = huobiClient.getKline(rightFormattedPair, interval, limit);
                if (kline.getData().isEmpty()) {
                    continue;
                }
                List<KlineDto> klinesDto = getKline(kline);
                klines.put(pair, KlineFullDataDtoMapper.toKlineFullDataDto(klinesDto));
            } catch (Exception e) {
                log.error("error from parse kline pair:{}, error: {}",pair, e.getMessage());
            }
        }
        return klines;
    }

    @Override
    public List<CoinInfoDto> getCoinsInformation() {
        CoinInfo coinsInformation = new CoinInfo();
        try {
            coinsInformation = huobiClient.getCoinsInformation();
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

                OrderBookDepth pairOrderBook = huobiClient
                        .getPairOrderBook(formattedPair, limit);

                orderBook.put(pair, OrderBookDtoMapper.orderBookDto(pairOrderBook));
            } catch (Exception e) {
                log.error("error from parse order book:{}, error: {}", pair, e.getMessage());
            }
        }
        return orderBook;
    }

    private List<KlineDto> getKline(KlineFull klineFull) {
        return klineFull.getData().stream()
                .map(KlineDtoMapper::toKlineDto)
                .collect(Collectors.toList());
    }

    private String formatPair(String pair) {
        String[] coin = pair.split("-");
        return String.format("%s%s", coin[0], coin[1]).toLowerCase();
    }
}
