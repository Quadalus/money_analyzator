package ru.bikkul.client;

import ru.bikkul.dto.OrderBookDto;
import ru.bikkul.dto.coin.CoinInfoDto;
import ru.bikkul.dto.KlineDataDto;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface ParserClient {
    Map<String, KlineDataDto> getKlineFromMarket(String port, Set<String> pairs);

    void sendKlinesDataToAnalyzer(Map<String, List<KlineDataDto>> klines);

    List<CoinInfoDto> getCoinInfoFromMarket(String port);

    Map<String, OrderBookDto> getOrderBookFromMarket(String port, Set<String> pairs);

    void sendOrdersDataToAnalyzator(Map<String, List<OrderBookDto>> orderBooks);
}
