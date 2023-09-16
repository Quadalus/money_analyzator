package ru.bikkul.parser.client;

import ru.bikkul.parser.domain.market.Kline;

import java.util.List;

public interface MexcClient {
    void testConnection();
    List<Kline> getKline(String symbol, String interval, Integer limit, long startTime, long endTime);
}
