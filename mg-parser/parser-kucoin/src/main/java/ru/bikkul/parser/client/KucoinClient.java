package ru.bikkul.parser.client;

import ru.bikkul.parser.domain.market.KlineFull;

public interface KucoinClient {
    KlineFull getKline(String symbol, String interval, Integer limit, long startTime, long endTime);
}
