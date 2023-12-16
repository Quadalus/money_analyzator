package ru.bikkul.parser.service;

import ru.bikkul.parser.domain.coin.CoinInfo;
import ru.bikkul.parser.dto.KlineFullDataDto;
import ru.bikkul.parser.dto.OrderBookDto;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface MexcParserService {
    Map<String, KlineFullDataDto> getKlineForFourMin(Set<String> pairs);

    List<CoinInfo> getCoinsInformation();

    Map<String, OrderBookDto> getSpotData(Set<String> pairs);
}
