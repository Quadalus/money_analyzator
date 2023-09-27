package ru.bikkul.parser.utils.mappers;

import com.binance.api.client.domain.market.OrderBookEntry;
import ru.bikkul.parser.dto.BidDto;

import java.time.LocalDateTime;

public class BidsDtoMapper {
    private BidsDtoMapper() {
    }

    public static BidDto toBidDto(OrderBookEntry orderBid, String pair) {
        BidDto bidDto = new BidDto();
        bidDto.setTime(LocalDateTime.now());
        bidDto.setPrice(orderBid.getPrice());
        bidDto.setQuantity(orderBid.getQty());
        bidDto.setPair(pair);
        return bidDto;
    }
}
