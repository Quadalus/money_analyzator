package ru.bikkul.parser.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.bikkul.parser.client.ProbitClientImpl;
import ru.bikkul.parser.domain.market.KlineFull;
import ru.bikkul.parser.domain.market.KlineInterval;
import ru.bikkul.parser.dto.KlineDto;
import ru.bikkul.parser.dto.KlineFullDataDTO;
import ru.bikkul.parser.utils.KlineDtoMapper;
import ru.bikkul.parser.utils.KlineFullDataDtoMapper;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProbitParserServiceImpl implements ProbitParserService {
    private final ProbitClientImpl probitClient;

    @Override
    public Map<String, KlineFullDataDTO> getKlineForFourMin(Set<String> pairs) {
        Map<String, KlineFullDataDTO> klines = new HashMap<>();
        Instant start = Instant.now().minusSeconds(300);
        String zStart = start.atZone(ZoneOffset.UTC).format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));
        Instant end = Instant.now();
        String zEnd = end.atZone(ZoneId.of("UTC")).format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));
        String interval = KlineInterval.ONE_MINUTE.getIntervalId();
        Integer limit = 5;
        String sort = "desc";

        for (String pair : pairs) {
            try {
                KlineFull kline = probitClient.getKline(pair, interval, limit, zStart, zEnd, sort);
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

    private List<KlineDto> getKline(KlineFull klineFull) {
        return klineFull.getData().stream()
                .map(KlineDtoMapper::toKlineDto)
                .collect(Collectors.toList());
    }
}
