package ru.bikkul.parser.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.bikkul.parser.client.BingxClientImpl;
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
public class BingxParserServiceImpl implements BingxParserService {
    private final BingxClientImpl bingx;

    @Override
    public Map<java.lang.String, KlineFullDataDTO> getKlineForFourMin(Set<String> pairs) {
        Map<java.lang.String, KlineFullDataDTO> klines = new HashMap<>();
        ObjectMapper objectMapper = new ObjectMapper();
        long start = Instant.now().minusSeconds(300).toEpochMilli();
        long end = Instant.now().toEpochMilli();
        String interval = KlineInterval.ONE_MINUTE.getIntervalId();

        for (String pair : pairs) {
            try {
                String kline = bingx.getKline(pair, interval, start, end);
                KlineFull klineFull = objectMapper.readValue(kline, KlineFull.class);
                List<KlineDto> klinesDto = getKline(klineFull);

                if (klinesDto.isEmpty()) {
                    continue;
                }
                klines.put(pair, KlineFullDataDtoMapper.toKlineFullDataDto(klinesDto));
            } catch (Exception e) {
                log.error("error from parse kline pair:{}, error: {}",pair, e.getMessage());
            }
        }
        return klines;
    }

    private List<KlineDto> getKline(KlineFull fullKline) {
        return fullKline.getData().getKlines().stream()
                .map(KlineDtoMapper::toKlineDto)
                .collect(Collectors.toList());
    }
}
