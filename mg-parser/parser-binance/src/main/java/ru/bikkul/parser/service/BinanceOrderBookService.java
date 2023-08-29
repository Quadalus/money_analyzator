package ru.bikkul.parser.service;

import com.binance.api.client.domain.market.OrderBook;

public interface BinanceOrderBookService {
    void collect();

    void save();

    void sendToParse();

    void parse(OrderBook orderBook, String pair);
}
