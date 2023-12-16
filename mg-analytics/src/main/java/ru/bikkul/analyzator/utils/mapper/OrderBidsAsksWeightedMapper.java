package ru.bikkul.analyzator.utils.mapper;

import ru.bikkul.analyzator.dto.OrderBookEntryDto;
import ru.bikkul.analyzator.dto.OrderBookWeightedDto;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class OrderBidsAsksWeightedMapper {
    private OrderBidsAsksWeightedMapper() {
    }

    public static OrderBookWeightedDto toOrderBookWeightedDto(List<OrderBookEntryDto> orderBookEntryDto) {
        OrderBookWeightedDto avgWeightedKlineDto = new OrderBookWeightedDto();
        BigDecimal avgVolume = getAvgVolume(orderBookEntryDto);
        BigDecimal expVolume = getExpVolume(orderBookEntryDto);

        avgWeightedKlineDto.setAvgWeighedPrice(getAvgWeightedPrice(orderBookEntryDto, expVolume));
        avgWeightedKlineDto.setAvgVolume(avgVolume);
        return avgWeightedKlineDto;
    }

    private static BigDecimal getAvgWeightedPrice(List<OrderBookEntryDto> orderBookEntryDto, BigDecimal expVolume) {
        BigDecimal avgWeightedPrice = BigDecimal.ZERO;
        BigDecimal volume;
        for (OrderBookEntryDto entryDto : orderBookEntryDto) {
            volume = entryDto.getQty();
            BigDecimal price = entryDto.getPrice();
            avgWeightedPrice = avgWeightedPrice.add(price.multiply(volume));
        }
        avgWeightedPrice = avgWeightedPrice.divide(expVolume, 6, RoundingMode.HALF_UP)
                .stripTrailingZeros();
        return avgWeightedPrice;
    }

    private static BigDecimal getAvgVolume(List<OrderBookEntryDto> orderBookEntryDto) {
        BigDecimal avgVolume = BigDecimal.ZERO;
        BigDecimal volume;
        for (OrderBookEntryDto entryDto : orderBookEntryDto) {
            volume = entryDto.getQty();
            avgVolume = avgVolume.add(volume);
        }
        avgVolume = avgVolume.divide(new BigDecimal(orderBookEntryDto.size()), 6, RoundingMode.HALF_UP);
        return avgVolume;
    }

    private static BigDecimal getExpVolume(List<OrderBookEntryDto> orderBookEntryDto) {
        BigDecimal expVolume = BigDecimal.ZERO;
        BigDecimal volume;
        for (OrderBookEntryDto entryDto : orderBookEntryDto) {
            volume = entryDto.getQty();
            expVolume = expVolume.add(volume);
        }
        return expVolume;
    }
}
