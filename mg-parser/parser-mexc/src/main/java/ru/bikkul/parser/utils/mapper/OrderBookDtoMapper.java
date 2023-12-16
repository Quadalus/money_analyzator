package ru.bikkul.parser.utils.mapper;

import ru.bikkul.parser.domain.market.OrderBook;
import ru.bikkul.parser.dto.OrderBookDto;

public class OrderBookDtoMapper {
    private OrderBookDtoMapper() {
    }

    public static OrderBook toOrderBook(OrderBookDto orderBookDto) {
        OrderBook orderBook = new OrderBook();
        orderBook.setAsks(orderBookDto.getAsks());
        orderBook.setBids(orderBookDto.getBids());
        return orderBook;
    }

    public static OrderBookDto orderBookDto(OrderBook orderBook) {
        OrderBookDto orderBookDto = new OrderBookDto();
        orderBookDto.setAsks(orderBook.getAsks());
        orderBookDto.setBids(orderBook.getBids());
        return orderBookDto;
    }
}
