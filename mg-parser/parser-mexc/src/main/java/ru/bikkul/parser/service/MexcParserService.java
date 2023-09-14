package ru.bikkul.parser.service;

import ru.bikkul.parser.dto.AvgWeightedKlineDto;

import java.util.Map;
import java.util.Set;

public interface MexcParserService {

    Map<String, AvgWeightedKlineDto> getKlineForFiveMin(Set<String> pairs);
}
