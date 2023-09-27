package ru.bikkul.client;

import ru.bikkul.dto.CoinInfoDto;
import ru.bikkul.dto.KlineDataDto;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface ParserClient {
    Map<String, KlineDataDto> getKlineFromMarket(String port, Set<String> pairs);

    void sendKlinesDataToAnalyzer(Map<String, List<KlineDataDto>> klines);

    Map<String, List<CoinInfoDto>> getCoinInfoFromMarket(String port);
}
