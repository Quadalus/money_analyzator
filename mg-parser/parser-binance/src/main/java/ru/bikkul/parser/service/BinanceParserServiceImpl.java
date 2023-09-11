package ru.bikkul.parser.service;

import com.binance.api.client.domain.market.OrderBook;
import com.binance.api.client.domain.market.OrderBookEntry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.bikkul.parser.client.BinanceParserClient;
import ru.bikkul.parser.model.Ask;
import ru.bikkul.parser.model.Bid;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BinanceParserServiceImpl implements BinanceParserService {
    private BinanceParserClient binanceParseClient;

    @Override
    public void getAllDate() {

    }

    @Override
    public void collect() {

    }

    @Override
    public void save() {

    }

    @Override
    public void sendToParse() {

    }

    @Override
    public void parse(OrderBook orderBook, String pair) {
        Map<String, Object> book = new HashMap<>();
        List<Bid> bids = parseBids(orderBook.getBids(), pair);
        List<Ask> asks = parseAsks(orderBook.getAsks(), pair);
    }

    @Override
    public void getPriceChangePercent(String percent, String pair) {

    }

    private List<Bid> parseBids(List<OrderBookEntry> bids, String pair) {
        return bids.stream()
                .map(orderBids -> {
                    Bid bid = new Bid();
                    bid.setTime(LocalDateTime.now());
                    bid.setPrice(orderBids.getPrice());
                    bid.setQuantity(orderBids.getQty());
                    bid.setPair(pair);
                    return bid;
                }).collect(Collectors.toList());
    }

    private List<Ask> parseAsks(List<OrderBookEntry> asks, String pair) {
        return asks.stream()
                .map(orderBids -> {
                    Ask ask = new Ask();
                    ask.setTime(LocalDateTime.now());
                    ask.setPrice(orderBids.getPrice());
                    ask.setQuantity(orderBids.getQty());
                    ask.setPair(pair);
                    return ask;
                }).collect(Collectors.toList());
    }
}
