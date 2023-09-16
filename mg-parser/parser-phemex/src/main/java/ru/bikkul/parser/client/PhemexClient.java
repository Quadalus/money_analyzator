package ru.bikkul.parser.client;

import ru.bikkul.parser.domain.market.KlineFull;

public interface PhemexClient {
    KlineFull getKline(String symbol, String interval, Integer limit);
}
