package ru.bikkul.parser.client;

import ru.bikkul.parser.domain.coin.CoinInfo;
import ru.bikkul.parser.domain.market.KlineFull;
import ru.bikkul.parser.domain.market.OrderBookDepth;

public interface OkxClient {
    KlineFull getKline(String symbol, String interval, Integer limit, long startTime, long endTime);

    CoinInfo getCoinsInformation();

    OrderBookDepth getPairOrderBook(String pair, Integer limit);
}
