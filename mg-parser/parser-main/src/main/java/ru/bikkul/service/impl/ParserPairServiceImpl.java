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

           /* "GALA-USDT",
            "CHZ-USDT",
            "CRV-USDT",
            "IMX-USDT",
            "MAGIC-USDT",
            "ARB-USDT",
            "DOT-USDT",
            "LINK-USDT",
            "1INCH-USDT",
            "LPT-USDT",
            "TRB-USDT",
            "LDO-USDT",
            "MATIC-USDT",
            "FET-USDT"*/
    public ParserPairServiceImpl() {
        pairs.add("CRV-USDT");
        pairs.add("MATIC-USDT");
        pairs.add("ARB-USDT");
        pairs.add("IMX-USDT");
        pairs.add("LINK-USDT");
        pairs.add("FET-USDT");
        pairs.add("LDO-USDT");
        pairs.add("TRB-USDT");
        pairs.add("1INCH-USDT");
        pairs.add("CHZ-USDT");
        pairs.add("LPT-USDT");
        pairs.add("DOT-USDT");
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
