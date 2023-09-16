package ru.bikkul.parser.client;

import ru.bikkul.parser.domain.market.KlineFull;

public interface OkxClient {
    KlineFull getKline(String symbol, String interval, Integer limit, long startTime, long endTime);
}