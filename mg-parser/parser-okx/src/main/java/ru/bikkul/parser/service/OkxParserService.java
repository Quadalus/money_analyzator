package ru.bikkul.parser.service;

import ru.bikkul.parser.dto.CoinInfoDto;
import ru.bikkul.parser.dto.KlineFullDataDTO;
import ru.bikkul.parser.dto.OrderBookDto;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface OkxParserService {

    Map<String, KlineFullDataDTO> getKlineForFourMin(Set<String> pairs);

    List<CoinInfoDto> getCoinsInformation();

    Map<String, OrderBookDto> getSpotData(Set<String> pairs);

}