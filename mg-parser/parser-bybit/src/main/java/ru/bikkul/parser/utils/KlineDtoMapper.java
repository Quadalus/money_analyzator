package ru.bikkul.parser.utils;

import ru.bikkul.parser.domain.market.Kline;
import ru.bikkul.parser.dto.KlineDto;

public class KlineDtoMapper {
    private KlineDtoMapper() {
    }

    public static KlineDto toKlineDto(Kline kline) {
        KlineDto klineDto = new KlineDto();

        klineDto.setClose(kline.getClose());
        klineDto.setVolume(kline.getVolume());
        return klineDto;
    }
}
