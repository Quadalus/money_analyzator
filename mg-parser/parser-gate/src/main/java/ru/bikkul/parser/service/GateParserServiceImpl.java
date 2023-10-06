package ru.bikkul.parser.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.bikkul.parser.client.GateClientImpl;
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
public class GateParserServiceImpl implements GateParserService {
    private final GateClientImpl mexcClient;

    @Override
    public Map<String, KlineFullDataDto> getKlineForFourMin(Set<String> pairs) {
        Map<String, KlineFullDataDto> klines = new HashMap<>();
        long msInSecond = 1000;
        long start = Instant.now().minusSeconds(290).toEpochMilli() / msInSecond;
        long end = Instant.now().toEpochMilli() / msInSecond;
        String interval = KlineInterval.ONE_MINUTE.getIntervalId();
        Integer limit = 4;

        for (String pair : pairs) {
            try {
                String rightFormattedPair = formatPair(pair);
                List<KlineDto> klineForFourMin = getKline(mexcClient.getKline(rightFormattedPair, interval, limit, start, end));
                if (klineForFourMin.isEmpty()) {
                    continue;
                }
                klines.put(pair, KlineFullDataDtoMapper.toKlineFullDataDto(klineForFourMin));
            } catch (Exception e) {
                log.error("error from parse kline pair:{}, error: {}", pair, e.getMessage());
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
        String separator = "_";
        return String.format("%s%s%s", coin[0], separator, coin[1]).toUpperCase();
    }
}
