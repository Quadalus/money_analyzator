package ru.bikkul.parser.service;

import ru.bikkul.parser.dto.KlineFullDataDto;

import java.util.Map;
import java.util.Set;

public interface LbankParserService {

    Map<String, KlineFullDataDto> getKlineForFiveMin(Set<String> pairs);
}
