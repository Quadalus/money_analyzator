package ru.bikkul.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.bikkul.service.ParserPairService;
import ru.bikkul.utils.Markets;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Slf4j
@Service
public class ParserPairServiceImpl implements ParserPairService {
    private final Map<Markets, Set<String>> pairs = new HashMap<>();

    public ParserPairServiceImpl() {
    }

    @Override
    public Map<Markets, Set<String>> getPairs() {
        return pairs;
    }

    @Override
    public void setPairs(Map<String, String> marketPairs) {
        for (Map.Entry<String, String> marketPair: marketPairs.entrySet()) {
            Markets key = Markets.valueOf(marketPair.getKey());
            Set<String> value = getMarketPairs(marketPair.getValue());

            if (!pairs.containsKey(key)) {
                pairs.put(key, new HashSet<>());
            }
            pairs.get(key).addAll(value);
        }
    }

    @Override
    public void deletePairs(Map<String, String> marketPairs) {
        for (Map.Entry<String, String> marketPair: marketPairs.entrySet()) {
            Markets key = Markets.valueOf(marketPair.getKey());
            Set<String> value = getMarketPairs(marketPair.getValue());

            pairs.get(key).removeAll(value);
        }
    }

    @Override
    public void deleteAllPairs() {
        pairs.clear();
    }

    Set<String> getMarketPairs(String marketPairs) {
        String[] pairs = marketPairs.split(",");
        return Set.of(pairs);
    }
}
