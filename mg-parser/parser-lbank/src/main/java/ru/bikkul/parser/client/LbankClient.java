package ru.bikkul.parser.client;

import ru.bikkul.parser.domain.market.KlineFull;

public interface LbankClient {
    void testConnection();
    KlineFull getKline(String symbol, String interval, Integer limit, long time);
}