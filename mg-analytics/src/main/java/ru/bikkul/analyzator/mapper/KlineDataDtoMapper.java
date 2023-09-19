package ru.bikkul.analyzator.mapper;

import ru.bikkul.analyzator.dto.KlineDataDto;
import ru.bikkul.analyzator.dto.KlineDataRequestDto;
import ru.bikkul.analyzator.dto.KlineDataResponseDto;
import ru.bikkul.analyzator.model.KlineSpread;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class KlineDataDtoMapper {
    private KlineDataDtoMapper() {

    }

    public static List<KlineDataDto> toKlinesDataDto(Map<String, List<KlineDataRequestDto>> klinesData) {
        return calculateSpread(klinesData);
    }

    public static List<KlineDataResponseDto> toKlinesDataResponseDto(List<KlineSpread> klineSpreads) {
        List<KlineDataResponseDto> klineDataDtos = new ArrayList<>();

        for (KlineSpread kline : klineSpreads) {
            klineDataDtos.add(fillKlineResponseDto(kline));
        }
        return klineDataDtos;
    }

    public static List<KlineDataDto> toKlinesDataDto(List<KlineSpread> klinesData) {
        List<KlineDataDto> klineDataDtos = new ArrayList<>();

        for (KlineSpread kline : klinesData) {
            klineDataDtos.add(fillKlineDto(kline));
        }
        return klineDataDtos;
    }

    public static List<KlineSpread> toKlineSpread(List<KlineDataDto> klineDataDto) {
        List<KlineSpread> klineSpread = new ArrayList<>();

        for (KlineDataDto klineDto : klineDataDto) {
            klineSpread.add(fillKlineSpread(klineDto));
        }
        return klineSpread;
    }

    private static List<KlineDataDto> calculateSpread(Map<String, List<KlineDataRequestDto>> valuePairData) {
        List<KlineDataDto> klineDataDtos = new ArrayList<>();

        for (Map.Entry<String, List<KlineDataRequestDto>> entry : valuePairData.entrySet()) {
            String pair = entry.getKey();
            List<KlineDataRequestDto> valuePairMarketData = entry.getValue();
            int size = valuePairMarketData.size();

            for (KlineDataRequestDto marketData : valuePairMarketData) {
                BigDecimal buyPriceFromMarket = marketData.getAvgWeighedPrice();
                for (int i = 0; i < size; i++) {
                    BigDecimal sellPriceFromMarket = valuePairMarketData.get(i).getAvgWeighedPrice();
                    String marketName = marketData.getMarketName();
                    String quoteMarketName = valuePairMarketData.get(i).getMarketName();
                    if (marketName.equals(quoteMarketName)) {
                        continue;
                    }
                    //формула: 100 * ((sellPrice/buyPrice) - 1)
                    BigDecimal buyMarketVolume = marketData.getAvgVolume();
                    BigDecimal sellMarketVolume = marketData.getAvgVolume();
                    BigDecimal buyMarketFee = new BigDecimal(marketData.getFee());
                    BigDecimal sellMarketFee = new BigDecimal(valuePairMarketData.get(i).getFee());
                    BigDecimal spread = (sellPriceFromMarket
                            .divide(buyPriceFromMarket, 9, RoundingMode.FLOOR))
                            .subtract(new BigDecimal(1))
                            .multiply(new BigDecimal(100))
                            .subtract(buyMarketFee)
                            .subtract(sellMarketFee)
                            .stripTrailingZeros();
                    klineDataDtos.add(new KlineDataDto(pair, marketName, quoteMarketName, buyPriceFromMarket, sellPriceFromMarket, buyMarketVolume, sellMarketVolume, spread, LocalDateTime.now()));
                }
            }
        }
        return klineDataDtos;
    }

    private static KlineDataDto fillKlineDto(KlineSpread klineSpread) {
        KlineDataDto klineDto = new KlineDataDto();
        klineDto.setPair(klineSpread.getPair());
        klineDto.setMarketBaseName(klineSpread.getMarketBaseName());
        klineDto.setMarketQuoteName(klineSpread.getMarketQuoteName());
        klineDto.setBasePrice(klineSpread.getBasePrice());
        klineDto.setQuotePrice(klineSpread.getQuotePrice());
        klineDto.setBaseVolume(klineSpread.getBaseVolume());
        klineDto.setQuoteVolume(klineSpread.getQuoteVolume());
        klineDto.setSpread(klineSpread.getSpread());
        klineDto.setTime(klineSpread.getTime());

        return klineDto;
    }

    private static KlineDataResponseDto fillKlineResponseDto(KlineSpread klineSpread) {
        KlineDataResponseDto klineDto = new KlineDataResponseDto();
        
        klineDto.setPair(klineSpread.getPair());
        klineDto.setMarketBaseName(klineSpread.getMarketBaseName());
        klineDto.setMarketQuoteName(klineSpread.getMarketQuoteName());
        klineDto.setBasePrice(klineSpread.getBasePrice());
        klineDto.setQuotePrice(klineSpread.getQuotePrice());
        klineDto.setBaseVolume(klineSpread.getBaseVolume());
        klineDto.setQuoteVolume(klineSpread.getQuoteVolume());
        klineDto.setSpread(klineSpread.getSpread());
        klineDto.setTime(klineSpread.getTime());
        fill25PercentageVolume(klineDto, klineDto.getBaseVolume(), klineDto.getQuoteVolume());

        return klineDto;
    }

    private static KlineSpread fillKlineSpread(KlineDataDto klineDto) {
        KlineSpread klineSpread = new KlineSpread();
        klineSpread.setPair(klineDto.getPair());
        klineSpread.setMarketBaseName(klineDto.getMarketBaseName());
        klineSpread.setMarketQuoteName(klineDto.getMarketQuoteName());
        klineSpread.setBasePrice(klineDto.getBasePrice());
        klineSpread.setQuotePrice(klineDto.getQuotePrice());
        klineSpread.setBaseVolume(klineDto.getBaseVolume());
        klineSpread.setQuoteVolume(klineDto.getQuoteVolume());
        klineSpread.setSpread(klineDto.getSpread());

        return klineSpread;
    }
    
    private static void fill25PercentageVolume(KlineDataResponseDto kline, BigDecimal baseVolume, BigDecimal quoteVolume) {
        BigDecimal percentageRatio = new BigDecimal("0.25");
        kline.setQuoteVolume25Percent(quoteVolume.multiply(percentageRatio));
        kline.setBaseVolume25Percent(baseVolume.multiply(percentageRatio));
    }
    
}
