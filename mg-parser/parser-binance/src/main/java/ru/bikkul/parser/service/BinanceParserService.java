package ru.bikkul.parser.service;

import com.binance.api.client.domain.market.Candlestick;
import com.binance.api.client.domain.market.OrderBook;
import ru.bikkul.parser.dto.AvgWeightedKlineDto;
import ru.bikkul.parser.dto.KlineDto;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface BinanceParserService {
    Map<String, AvgWeightedKlineDto> getKlineForFiveMin(Set<String> pairs);

    void getSpotData(OrderBook orderBook, String pair);
}
