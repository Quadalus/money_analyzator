package ru.bikkul.parser.utils.mappers;

import com.binance.api.client.domain.market.OrderBookEntry;
import ru.bikkul.parser.dto.AskDto;

import java.time.LocalDateTime;

public class AskDtoMapper {
    private AskDtoMapper() {
    }

    public static AskDto toAskDto(OrderBookEntry orderAsk, String pair) {
        AskDto askDto = new AskDto();
        askDto.setTime(LocalDateTime.now());
        askDto.setPrice(orderAsk.getPrice());
        askDto.setQuantity(orderAsk.getQty());
        askDto.setPair(pair);
        return askDto;
    }
}
