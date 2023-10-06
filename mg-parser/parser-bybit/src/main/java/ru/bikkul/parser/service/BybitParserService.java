package ru.bikkul.parser.service;

import ru.bikkul.parser.dto.CoinInfoDto;
import ru.bikkul.parser.dto.KlineFullDataDTO;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface BybitParserService {

    Map<String, KlineFullDataDTO> getKlineForFiveMin(Set<String> pairs);

    List<CoinInfoDto> getCoinsInformation();

}
