package ru.bikkul.parser.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.bikkul.parser.client.MexcClientImpl;
import ru.bikkul.parser.domain.market.Kline;
import ru.bikkul.parser.domain.market.KlineInterval;
import ru.bikkul.parser.dto.KlineFullDataDto;
import ru.bikkul.parser.dto.KlineDto;
import ru.bikkul.parser.utils.KlineFullDataDtoMapper;
import ru.bikkul.parser.utils.KlineDtoMapper;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MexcParserServiceImpl implements MexcParserService {
    private final MexcClientImpl mexcClient;

    @Override
    public Map<String, KlineFullDataDto> getKlineForFiveMin(Set<String> pairs) {
        Map<String, KlineFullDataDto> klines = new HashMap<>();
        long start = Instant.now().minusSeconds(360).toEpochMilli();
        long end = Instant.now().toEpochMilli();
        String interval = KlineInterval.ONE_MINUTE.getIntervalId();
        Integer limit = 10;

        for (String pair : pairs) {
            List<KlineDto> klineForFiveMin = getKline(mexcClient.getKline(pair, interval, limit, start, end));
            klines.put(pair, KlineFullDataDtoMapper.toKlineFullDataDto(klineForFiveMin));
        }
        return klines;
    }

    private List<KlineDto> getKline(List<Kline> klines) {
        return klines.stream()
                .map(KlineDtoMapper::toKlineDto)
                .collect(Collectors.toList());
    }
}
