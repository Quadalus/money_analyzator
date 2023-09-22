package ru.bikkul.parser.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.bikkul.parser.client.AscendexClientImpl;
import ru.bikkul.parser.domain.market.KlineFull;
import ru.bikkul.parser.domain.market.KlineInterval;
import ru.bikkul.parser.dto.KlineDto;
import ru.bikkul.parser.dto.KlineFullDataDTO;
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
public class AscendexParserServiceImpl implements AscendexParserService {
    private final AscendexClientImpl ascendexClient;

    @Override
    public Map<java.lang.String, KlineFullDataDTO> getKlineForFourMin(Set<String> pairs) {
        Map<java.lang.String, KlineFullDataDTO> klines = new HashMap<>();
        ObjectMapper objectMapper = new ObjectMapper();
        long start = Instant.now().minusSeconds(300).toEpochMilli();
        long end = Instant.now().toEpochMilli();
        int limit = 5;
        String interval = KlineInterval.ONE_MINUTE.getIntervalId();

        for (String pair : pairs) {
            try {
                String formattedPair = formatPair(pair);
                String kline = ascendexClient.getKline(formattedPair, interval, start, end, limit);
                KlineFull klineFull = objectMapper.readValue(kline, KlineFull.class);
                List<KlineDto> klinesDto = getKlineDto(klineFull);

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

    private List<KlineDto> getKlineDto(KlineFull klineFull) {
        return klineFull.getData().stream()
                .map(klineData -> KlineDtoMapper.toKlineDto(klineData.getData()))
                .collect(Collectors.toList());
    }

    private String formatPair(String pair) {
        String[] coin = pair.split("-");
        String splitSymbol = "/";
        return String.format("%s%s%s", coin[0], splitSymbol, coin[1]).toUpperCase();
    }
}
