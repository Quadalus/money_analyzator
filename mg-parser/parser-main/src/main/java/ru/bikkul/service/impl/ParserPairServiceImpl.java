package ru.bikkul.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.bikkul.service.ParserPairService;
import ru.bikkul.utils.Markets;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static ru.bikkul.utils.Pairs.*;

@Slf4j
@Service
public class ParserPairServiceImpl implements ParserPairService {
    private final Map<Markets, Set<String>> pairs = new HashMap<>();

    public ParserPairServiceImpl() {
        pairs.put(Markets.BINANCE, Set.of(BTC_USDT.getPair(), ETH_BTC.getPair(), ETH_USDT.getPair(), IMX_USDT.getPair(),
                STX_USDT.getPair(), ALGO_USDT.getPair(), GRT_USDT.getPair(), AAVE_USDT.getPair(), RNDR_USDT.getPair(),
                XTZ_USDT.getPair(), EOS_USDT.getPair(), THETA_USDT.getPair(), SAND_USDT.getPair(), MANA_USDT.getPair(),
                FTM_USDT.getPair(), SNX_USDT.getPair(), OP_USDT.getPair(), KAVA_USDT.getPair(), NEO_USDT.getPair(),
                PAXG_USDT.getPair(), FLOW_USDT.getPair(), CHZ_USDT.getPair(), ZEC_USDT.getPair(), OCEAN_USDT.getPair(),
                CFX_USDT.getPair(), APE_USDT.getPair(), RPL_USDT.getPair(), SOL_USDT.getPair(), ADA_USDT.getPair(),
                DOGE_USDT.getPair(), TRX_USDT.getPair(), MATIC_USDT.getPair(), ASTR_USDT.getPair()
        ));
        pairs.put(Markets.BYBIT, Set.of(BTC_USDT.getPair(), ETH_BTC.getPair(), ETH_USDT.getPair(), IMX_USDT.getPair(),
                XDC_USDT.getPair(), STX_USDT.getPair(), USDD_USDT.getPair(), ALGO_USDT.getPair(), GRT_USDT.getPair(),
                AAVE_USDT.getPair(), RNDR_USDT.getPair(), XTZ_USDT.getPair(), INJ_USDT.getPair(), EGLD_USDT.getPair(),
                EOS_USDT.getPair(), THETA_USDT.getPair(), SAND_USDT.getPair(), MANA_USDT.getPair(), FTM_USDT.getPair(),
                SNX_USDT.getPair(), OP_USDT.getPair(), KAVA_USDT.getPair(), NEO_USDT.getPair(), PAXG_USDT.getPair(),
                FLOW_USDT.getPair(), CHZ_USDT.getPair(), CRV_USDT.getPair(), APE_USDT.getPair(), RPL_USDT.getPair(),
                SOL_USDT.getPair(), ADA_USDT.getPair(), DOGE_USDT.getPair(), TRX_USDT.getPair(), MATIC_USDT.getPair(),
                MX_USDT.getPair()
        ));
        pairs.put(Markets.MEXC, Set.of(BTC_USDT.getPair(), ETH_BTC.getPair(), ETH_USDT.getPair(), IMX_USDT.getPair(),
                STX_USDT.getPair(), BSV_USDT.getPair(), USDD_USDT.getPair(), ALGO_USDT.getPair(), GRT_USDT.getPair(),
                AAVE_USDT.getPair(), RNDR_USDT.getPair(), XTZ_USDT.getPair(), INJ_USDT.getPair(), EGLD_USDT.getPair(),
                EOS_USDT.getPair(), SAND_USDT.getPair(), MANA_USDT.getPair(), FTM_USDT.getPair(), SNX_USDT.getPair(),
                OP_USDT.getPair(), KAVA_USDT.getPair(), NEO_USDT.getPair(), PAXG_USDT.getPair(), XAUT_USDT.getPair(),
                FLOW_USDT.getPair(), CHZ_USDT.getPair(), ZEC_USDT.getPair(), OCEAN_USDT.getPair(), CFX_USDT.getPair(),
                CRV_USDT.getPair(), APE_USDT.getPair(), RPL_USDT.getPair(), SOL_USDT.getPair(), ADA_USDT.getPair(),
                DOGE_USDT.getPair(), TRX_USDT.getPair(), MATIC_USDT.getPair(), ASTR_USDT.getPair(), MX_USDT.getPair()
        ));
        pairs.put(Markets.HUOBI, Set.of(BTC_USDT.getPair(), ETH_BTC.getPair(), ETH_USDT.getPair(), IMX_USDT.getPair(),
                XDC_USDT.getPair(), BSV_USDT.getPair(), USDD_USDT.getPair(), ALGO_USDT.getPair(), GRT_USDT.getPair(),
                AAVE_USDT.getPair(), RNDR_USDT.getPair(), XTZ_USDT.getPair(), INJ_USDT.getPair(), EGLD_USDT.getPair(),
                EOS_USDT.getPair(), THETA_USDT.getPair(), SAND_USDT.getPair(), MANA_USDT.getPair(), FTM_USDT.getPair(),
                SNX_USDT.getPair(), OP_USDT.getPair(), KAVA_USDT.getPair(), NEO_USDT.getPair(), XAUT_USDT.getPair(),
                FLOW_USDT.getPair(), CHZ_USDT.getPair(), ZEC_USDT.getPair(), OCEAN_USDT.getPair(), CRV_USDT.getPair(),
                APE_USDT.getPair(), RPL_USDT.getPair(), SOL_USDT.getPair(), ADA_USDT.getPair(), DOGE_USDT.getPair(),
                TRX_USDT.getPair(), MATIC_USDT.getPair(), ASTR_USDT.getPair(), MX_USDT.getPair()
        ));
        pairs.put(Markets.OKX, Set.of(BTC_USDT.getPair(), ETH_BTC.getPair(), ETH_USDT.getPair(), IMX_USDT.getPair(),
                STX_USDT.getPair(), BSV_USDT.getPair(), ALGO_USDT.getPair(), GRT_USDT.getPair(), AAVE_USDT.getPair(),
                RNDR_USDT.getPair(), XTZ_USDT.getPair(), EGLD_USDT.getPair(), EOS_USDT.getPair(), THETA_USDT.getPair(),
                SAND_USDT.getPair(), MANA_USDT.getPair(), FTM_USDT.getPair(), SNX_USDT.getPair(), OP_USDT.getPair(),
                XAUT_USDT.getPair(), FLOW_USDT.getPair(), CHZ_USDT.getPair(), ZEC_USDT.getPair(), CFX_USDT.getPair(),
                CRV_USDT.getPair(), APE_USDT.getPair(), RPL_USDT.getPair(), SOL_USDT.getPair(), ADA_USDT.getPair(),
                DOGE_USDT.getPair(), TRX_USDT.getPair(), MATIC_USDT.getPair(), ASTR_USDT.getPair()
        ));
    }

    @Override
    public Map<Markets, Set<String>> getPairs() {
        return pairs;
    }

    @Override
    public void setPairs(Map<String, String> marketPairs) {
        for (Map.Entry<String, String> marketPair : marketPairs.entrySet()) {
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
        for (Map.Entry<String, String> marketPair : marketPairs.entrySet()) {
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
