package ru.bikkul.parser.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.bikkul.parser.client.KrakenClient;
import ru.bikkul.parser.domain.market.Kline;
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
public class KrakenParserServiceImpl implements KrakenParserService {
    private final KrakenClient krakenClient;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Map<String, KlineFullDataDTO> getKlineForFourMin(Set<String> pairs) {
        Map<String, KlineFullDataDTO> klines = new HashMap<>();
        long start = Instant.now().minusSeconds(360).toEpochMilli() / 1000;
        String interval = KlineInterval.ONE_MINUTE.getIntervalId();

        for (String pair : pairs) {
            try {
                KlineFull klineFull = getKlineFull(start, interval, pair);
                List<Kline> klinesData = getKlinesData(klineFull);
                List<KlineDto> klinesDto = getKline(klinesData);

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

    private KlineFull getKlineFull(long start, String interval, String pair) throws JsonProcessingException {
        String formattedPair = formatPair(pair);
        String kline = krakenClient.getKline(formattedPair, interval, start);
        return objectMapper.readValue(kline, KlineFull.class);
    }

    private List<Kline> getKlinesData(KlineFull klineFull) throws JsonProcessingException {
        String jsonData = klineFull.getResult().getData();
        return objectMapper.readValue(jsonData, new TypeReference<List<Kline>>() {});
    }

    private List<KlineDto> getKline(List<Kline> klines) {
        return klines.stream()
                .map(KlineDtoMapper::toKlineDto)
                .collect(Collectors.toList());
    }

    private String formatPair(String pair) {
        String[] coin = pair.split("-");
        return String.format("%s%s", coin[0], coin[1]).toUpperCase();
    }
}
