package ru.bikkul.parser.client;

import ru.bikkul.parser.domain.coin.CoinInfo;
import ru.bikkul.parser.domain.market.KlineFull;

public interface HuobiClient {
    KlineFull getKline(String symbol, String interval, Integer limit);

    CoinInfo getCoinsInformation();

}
