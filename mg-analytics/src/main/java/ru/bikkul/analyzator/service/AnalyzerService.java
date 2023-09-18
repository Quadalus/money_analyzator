package ru.bikkul.analyzator.service;

import ru.bikkul.analyzator.dto.KlineDataDto;
import ru.bikkul.analyzator.dto.KlineDataRequestDto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface AnalyzerService {
    List<KlineDataDto> saveKlinesData(Map<String, List<KlineDataRequestDto>> klinesData);

    BigDecimal setSpreadTarget(String spreadTarget);

    BigDecimal getSpreadTarget();
}
