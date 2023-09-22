package ru.bikkul.service;

import java.util.Set;

public interface ParserPairService {
    void setPairs(Set<String> pairs);

    void addPair(String pair);

    void deletePairs(Set<String> pairs);

    void deletePair(String pair);

    void deleteAllPairs();

    Set<String> getPairs();
}
