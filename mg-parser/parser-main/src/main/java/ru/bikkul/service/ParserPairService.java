package ru.bikkul.service;

import ru.bikkul.utils.Markets;

import java.util.Map;
import java.util.Set;

public interface ParserPairService {
    void setPairs(Map<String,String> marketPairs);

    void deletePairs(Map<String, String> marketPairs);

    void deleteAllPairs();

    Map<Markets, Set<String>> getPairs();
}
