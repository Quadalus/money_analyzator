package ru.bikkul.parser.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.bikkul.parser.client.BitgetClient;
import ru.bikkul.parser.domain.market.Kline;
import ru.bikkul.parser.domain.market.KlineInterval;
import ru.bikkul.parser.dto.KlineDto;
import ru.bikkul.parser.dto.KlineFullDataDto;
import ru.bikkul.parser.utils.KlineDtoMapper;
import ru.bikkul.parser.utils.KlineFullDataDtoMapper;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class BitgetParserServiceImpl implements BitgetParserService {
    private final BitgetClient bitgetClient;

    @Override
    public Map<String, KlineFullDataDto> getKlineForFourMin(Set<String> pairs) {
        Map<String, KlineFullDataDto> klines = new HashMap<>();
        long start = Instant.now().minusSeconds(300).toEpochMilli();
        long end = Instant.now().toEpochMilli();
        String interval = KlineInterval.ONE_MINUTE.getIntervalId();
        Integer limit = 5;

        for (String pair : pairs) {
            try {
                String rightFormattedPair = formatPair(pair);
                List<KlineDto> klineForFourMin = getKline(bitgetClient.getKline(rightFormattedPair, interval, limit, start, end));
                if (klineForFourMin.isEmpty()) {
                    continue;
                }
                klines.put(pair, KlineFullDataDtoMapper.toKlineFullDataDto(klineForFourMin));
            } catch (Exception e) {
                log.error("error from parser klines, exception msg:{}", e.getMessage());
            }
        }
        return klines;
    }

    private List<KlineDto> getKline(List<Kline> klines) {
        return klines.stream()
                .map(KlineDtoMapper::toKlineDto)
                .collect(Collectors.toList());
    }

    private String formatPair(String pair) {
        String[] coin = pair.split("-");
        String postfix = "_UMCBL";
        return String.format("%s%s%s", coin[0], coin[1], postfix).toUpperCase();
    }
}
