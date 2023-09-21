package ru.bikkul.service;

import java.util.Set;

public interface ParserService {
    void setPairs(Set<String> pairs);

    void addPair(String pair);

    void deletePairs(Set<String> pairs);

    void deletePair(String pair);

    void deleteAllPairs();

    void clearKlinesData();

    void initParser();

    Set<String> getPairs();
}
