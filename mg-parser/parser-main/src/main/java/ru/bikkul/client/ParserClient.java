package ru.bikkul.client;

import ru.bikkul.model.KlineData;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

public interface ParserClient {
    HashMap<String, List<KlineData>> getKlineFromMarket(Set<String> pairs);
}
