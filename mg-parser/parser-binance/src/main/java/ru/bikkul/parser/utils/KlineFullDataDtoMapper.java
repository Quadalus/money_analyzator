package ru.bikkul.parser.utils;

import ru.bikkul.parser.dto.KlineFullDataDto;
import ru.bikkul.parser.dto.KlineDto;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class KlineFullDataDtoMapper {
    private KlineFullDataDtoMapper() {
    }

    public static KlineFullDataDto toAvgWeightedKlineDto(List<KlineDto> klineDto) {
        KlineFullDataDto avgWeightedKlineDto = new KlineFullDataDto();
        BigDecimal avgVolume = getAvgVolume(klineDto);
        BigDecimal expVolume = getExpVolume(klineDto);

        avgWeightedKlineDto.setAvgWeighedPrice(getAvgWeightedPrice(klineDto, expVolume));
        avgWeightedKlineDto.setAvgVolume(avgVolume);
        return avgWeightedKlineDto;
    }

    private static BigDecimal getAvgWeightedPrice(List<KlineDto> kline, BigDecimal expVolume) {
        BigDecimal avgWeightedPrice = BigDecimal.ZERO;
        BigDecimal volume;
        for (KlineDto klineDto : kline) {
            volume = new BigDecimal(klineDto.getVolume());
            BigDecimal price = new BigDecimal(klineDto.getClose());
            avgWeightedPrice = avgWeightedPrice.add(price.multiply(volume));
        }
        avgWeightedPrice = avgWeightedPrice.divide(expVolume, 10, RoundingMode.HALF_UP)
                .stripTrailingZeros();
        return avgWeightedPrice;
    }

    private static BigDecimal getAvgVolume(List<KlineDto> kline) {
        BigDecimal avgVolume = BigDecimal.ZERO;
        BigDecimal volume;
        for (KlineDto klineDto : kline) {
            volume = new BigDecimal(klineDto.getVolume());
            avgVolume = avgVolume.add(volume);
        }
        avgVolume = avgVolume.divide(new BigDecimal(kline.size()), 10, RoundingMode.HALF_UP);
        return avgVolume;
    }

    private static BigDecimal getExpVolume(List<KlineDto> kline) {
        BigDecimal expVolume = BigDecimal.ZERO;
        BigDecimal volume;
        for (KlineDto klineDto : kline) {
            volume = new BigDecimal(klineDto.getVolume());
            expVolume = expVolume.add(volume);
        }
        return expVolume;
    }
}
