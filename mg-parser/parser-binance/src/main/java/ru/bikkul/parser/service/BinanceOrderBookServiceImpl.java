package ru.bikkul.parser.service;

import com.binance.api.client.domain.market.OrderBook;
import com.binance.api.client.domain.market.OrderBookEntry;
import org.springframework.stereotype.Service;
import ru.bikkul.parser.model.Ask;
import ru.bikkul.parser.model.Bid;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BinanceOrderBookServiceImpl implements BinanceOrderBookService {

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
        List<OrderBookEntry> bids = orderBook.getBids();
        List<OrderBookEntry> asks = orderBook.getAsks();
        System.out.println("BIDS:\n" + parseBids(bids, pair));
        System.out.println("ASKS:\n" + parseAsks(asks, pair));
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
