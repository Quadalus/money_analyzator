package ru.bikkul.parser.service;

import ru.bikkul.parser.dto.KlineFullDataDTO;

import java.util.Map;
import java.util.Set;

public interface KucoinParserService {
    Map<String, KlineFullDataDTO> getKlineForFourMin(Set<String> pairs);
}
