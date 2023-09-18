package ru.bikkul.service;

import ru.bikkul.model.KlineData;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface ParserService {
    void setPairs(Set<String> pairs);

    void addPair(String pair);

    void deletePairs(Set<String> pairs);

    void deletePair(String pair);

    void deleteAllPairs();

    Map<String, List<KlineData>> getAllKlinesData();

    void clearKlinesData();

    void initParser();
}
