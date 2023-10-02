package ru.bikkul.parser.client;

import ru.bikkul.parser.domain.coin.CoinInfo;
import ru.bikkul.parser.domain.market.KlineFull;

import java.util.List;

public interface OkxClient {
    KlineFull getKline(String symbol, String interval, Integer limit, long startTime, long endTime);

    List<CoinInfo> getCoinsInformation();
}
