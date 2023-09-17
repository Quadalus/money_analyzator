package ru.bikkul.client;

import ru.bikkul.model.KlineData;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface ParserClient {
    Map<String, List<KlineData>> getKlineFromMarket(String port, Set<String> pairs);
}
