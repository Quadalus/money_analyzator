package ru.bikkul.client;

import ru.bikkul.model.KlineData;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface ParserClient {
    Map<String, KlineData> getKlineFromMarket(String port, Set<String> pairs);

    void sendKlinesDataToAnalyzer(Map<String, List<KlineData>> klines);
}
