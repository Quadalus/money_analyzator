package ru.bikkul.parser.service;

import com.binance.api.client.domain.market.OrderBook;

public interface BinanceParserService {

    void getAllDate();
    void collect();

    void save();

    void sendToParse();

    void parse(OrderBook orderBook, String pair);

    void getPriceChangePercent(String percent, String pair);
}
