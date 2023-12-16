package ru.bikkul.analyzator.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import ru.bikkul.analyzator.dto.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface AnalyzerService {
    List<KlineDataDto> saveKlinesData(String klinesData);

    List<OrderBookSpreadDto> saveOrderBookData(String klinesData);

    BigDecimal setSpreadTarget(String spreadTarget);

    BigDecimal getSpreadTarget();

    List<String> getOrderData();

    List<String> getKlineData();
}
