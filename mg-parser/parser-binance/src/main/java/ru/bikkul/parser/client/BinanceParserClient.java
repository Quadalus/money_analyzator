package ru.bikkul.parser.client;

import com.binance.api.client.domain.market.Candlestick;
import ru.bikkul.parser.domain.coin.CoinInfo;

import java.util.List;

public interface BinanceParserClient {
    List<Candlestick> getKlineForFiveMin(String pair, String interval, Long start, Long end, Integer limit);

    List<CoinInfo> getCoinsInformation(Long timestamp);
}
