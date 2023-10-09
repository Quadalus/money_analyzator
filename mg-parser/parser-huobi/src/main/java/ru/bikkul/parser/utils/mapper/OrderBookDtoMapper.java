package ru.bikkul.parser.utils.mapper;

import ru.bikkul.parser.domain.market.OrderBookDepth;
import ru.bikkul.parser.dto.OrderBookDto;

public class OrderBookDtoMapper {
    private OrderBookDtoMapper() {
    }

    public static OrderBookDto orderBookDto(OrderBookDepth orderBook) {
        OrderBookDto orderBookDto = new OrderBookDto();
        orderBookDto.setAsks(orderBook.getTick().getAsks());
        orderBookDto.setBids(orderBook.getTick().getBids());
        return orderBookDto;
    }
}
