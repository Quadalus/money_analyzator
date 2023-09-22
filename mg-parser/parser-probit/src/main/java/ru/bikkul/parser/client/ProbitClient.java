package ru.bikkul.parser.client;

import ru.bikkul.parser.domain.market.KlineFull;

public interface ProbitClient {
    KlineFull getKline(String symbol, String interval, Integer limit, String startTime, String endTime, String sort);
}
