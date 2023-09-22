package ru.bikkul.parser.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.bikkul.parser.client.BybitClientImpl;
import ru.bikkul.parser.domain.market.KlineFull;
import ru.bikkul.parser.domain.market.KlineInterval;
import ru.bikkul.parser.dto.KlineFullDataDTO;
import ru.bikkul.parser.dto.KlineDto;
import ru.bikkul.parser.utils.KlineFullDataDtoMapper;
import ru.bikkul.parser.utils.KlineDtoMapper;

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
                log.error("error from parse kline, error: {}", e.getMessage());
            }
        }
        return klines;
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
