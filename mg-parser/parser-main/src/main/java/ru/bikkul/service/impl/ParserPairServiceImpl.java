package ru.bikkul.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.bikkul.service.ParserPairService;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@Service
public class ParserPairServiceImpl implements ParserPairService {
    private final Set<String> pairs = new HashSet<>();

    public ParserPairServiceImpl() {
        pairs.add("XLM-USDT");
        pairs.add("XMR-USDT");
        pairs.add("MATIC-USDT");
        pairs.add("TRX-USDT");
        pairs.add("ARB-USDT");
        pairs.add("IMX-USDT");
        pairs.add("SOL-USDT");
        pairs.add("COMP-USDT");
        pairs.add("DASH-USDT");
    }

    @Override
    public Set<String> getPairs() {
        return pairs;
    }

    @Override
    public void setPairs(Set<String> pairs) {
        this.pairs.addAll(pairs);
    }

    @Override
    public void addPair(String pair) {
        this.pairs.add(pair);
    }

    @Override
    public void deletePairs(Set<String> pairs) {
        this.pairs.removeAll(pairs);
    }

    @Override
    public void deletePair(String pair) {
        this.pairs.remove(pair);
    }

    @Override
    public void deleteAllPairs() {
        pairs.clear();
    }
}
