package ru.bikkul.parser.utils.mappers;

import com.binance.api.client.domain.market.Candlestick;
import ru.bikkul.parser.dto.KlineDto;

public class KlineDtoMapper {
    private KlineDtoMapper() {
    }

    public static KlineDto toKlineDto(Candlestick candlestick) {
        KlineDto klineDto = new KlineDto();

        klineDto.setClose(candlestick.getClose());
        klineDto.setVolume(candlestick.getVolume());
        return klineDto;
    }
}
