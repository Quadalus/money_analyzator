package ru.bikkul.parser.client;

import ru.bikkul.parser.domain.market.Kline;

import java.util.List;

public interface GateClient {
    List<Kline> getKline(String symbol, String interval, Integer limit, long startTime, long endTime);
}
