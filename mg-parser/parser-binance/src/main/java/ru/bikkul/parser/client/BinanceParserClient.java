package ru.bikkul.parser.client;

import ru.bikkul.parser.domain.coin.CoinInfo;
import ru.bikkul.parser.domain.market.Candlestick;
import ru.bikkul.parser.domain.market.OrderBook;

import java.util.List;

public interface BinanceParserClient {
    List<Candlestick> getKlineForFiveMin(String pair, String interval, Long start, Long end, Integer limit);

    List<CoinInfo> getCoinsInformation(Long timestamp);

    OrderBook getPairOrderBook(String pair, Integer limit);
}
