package ru.bikkul.parser.service;

import com.binance.api.client.domain.market.OrderBook;
import ru.bikkul.parser.dto.KlineFullDataDto;

import java.util.Map;
import java.util.Set;

public interface BinanceParserService {
    Map<String, KlineFullDataDto> getKlineForFiveMin(Set<String> pairs);

    void getSpotData(OrderBook orderBook, String pair);
}
