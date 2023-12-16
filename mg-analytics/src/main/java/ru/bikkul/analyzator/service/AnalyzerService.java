package ru.bikkul.analyzator.service;

import ru.bikkul.analyzator.dto.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface AnalyzerService {
    List<KlineDataDto> saveKlinesData(Map<String, List<KlineDataRequestDto>> klinesData);

    List<OrderBookSpreadDto> saveOrderBookData(Map<String, List<OrderBookRequestDto>> klinesData);

    BigDecimal setSpreadTarget(String spreadTarget);

    BigDecimal getSpreadTarget();

    List<String> getOrderData();

    List<String> getKlineData();
}
