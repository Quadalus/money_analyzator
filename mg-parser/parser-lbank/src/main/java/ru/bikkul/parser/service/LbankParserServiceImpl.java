package ru.bikkul.parser.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.bikkul.parser.client.LbankClientImpl;
import ru.bikkul.parser.domain.market.KlineFull;
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

@Service
@RequiredArgsConstructor
public class LbankParserServiceImpl implements LbankParserService {
    private final LbankClientImpl lbankClient;

    @Override
    public Map<String, KlineFullDataDto> getKlineForFiveMin(Set<String> pairs) {
        Map<String, KlineFullDataDto> klines = new HashMap<>();
        long start = Instant.now().minusSeconds(360).toEpochMilli() / 1000;
        String interval = KlineInterval.ONE_MINUTE.getIntervalId();
        Integer limit = 6;

        for (String pair : pairs) {
            String rightFormattedPair = formatPair(pair);
            List<KlineDto> klineForFiveMin = getKline(lbankClient.getKline(rightFormattedPair, interval, limit, start));
            klines.put(pair, KlineFullDataDtoMapper.toKlineFullDataDto(klineForFiveMin));
        }
        return klines;
    }

    private List<KlineDto> getKline(KlineFull klineFull) {
        return klineFull.getData().stream()
                .map(KlineDtoMapper::toKlineDto)
                .collect(Collectors.toList());
    }

    private String formatPair(String pair) {
        String[] coin = pair.split("-");
        String separator = "_";
        return String.format("%s%s%s", coin[0], separator, coin[1]).toLowerCase();
    }
}
