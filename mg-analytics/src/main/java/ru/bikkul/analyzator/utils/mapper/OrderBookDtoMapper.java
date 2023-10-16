package ru.bikkul.analyzator.utils.mapper;

import ru.bikkul.analyzator.dto.*;
import ru.bikkul.analyzator.model.OrderBookSpread;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderBookDtoMapper {
    private OrderBookDtoMapper() {
    }

    public static Map<String, List<OrderBookDataDto>> toOrderBookDataDto(Map<String, List<OrderBookRequestDto>> orderBookRequestDto) {
        Map<String, List<OrderBookDataDto>> orderBookDataDto = new HashMap<>();

        for (Map.Entry<String, List<OrderBookRequestDto>> entry : orderBookRequestDto.entrySet()) {
            List<OrderBookRequestDto> orderBookDto = entry.getValue();
            String pairName = entry.getKey();

            for (OrderBookRequestDto orderBook : orderBookDto) {
                OrderBookDataDto orderBookData = new OrderBookDataDto();
                orderBookData.setMarketName(orderBook.getMarketName());
                orderBookData.setMarketType(orderBook.getMarketType());
                orderBookData.setBids(getBids(orderBook));
                orderBookData.setAsks(getAsks(orderBook));
                orderBookData.setFee(orderBook.getFee());
                orderBookData.setTime(orderBook.getTime());

                if (!orderBookDataDto.containsKey(pairName)) {
                    orderBookDataDto.put(pairName, new ArrayList<>());
                }
                orderBookDataDto.get(pairName).add(orderBookData);
            }
        }
        return orderBookDataDto;
    }

    public static List<OrderBookSpreadDto> toOrderBookWeightedDto(Map<String, List<OrderBookDataDto>> orderBookDto) {
        List<OrderBookSpreadDto> orderBookSpreadDto = new ArrayList<>();

        for (Map.Entry<String, List<OrderBookDataDto>> entry : orderBookDto.entrySet()) {
            String pair = entry.getKey();
            List<OrderBookDataDto> orderBookDataDto = entry.getValue();
            int size = orderBookDataDto.size();

            for (OrderBookDataDto orderBook : orderBookDataDto) {
                List<OrderBookEntryDto> baseAsks = orderBook.getAsks();
                BigDecimal baseTargetAskPrice = new BigDecimal("0");

                for (int i = 0; i < size; i++) {
                    String marketName = orderBook.getMarketName();
                    String quoteMarketName = orderBookDataDto.get(i).getMarketName();

                    if (marketName.equals(quoteMarketName)) {
                        continue;
                    }
                    List<OrderBookEntryDto> baseBookEntry = new ArrayList<>();
                    List<OrderBookEntryDto> quoteBookEntry = new ArrayList<>();
                    List<OrderBookEntryDto> quoteBids = orderBookDataDto.get(i).getBids();

                    baseTargetAskPrice = checkBids(baseAsks, baseTargetAskPrice, quoteBookEntry, quoteBids);
                    fillBaseBookEntry(baseAsks, baseTargetAskPrice, baseBookEntry);
                    calculateSpread(orderBookSpreadDto, pair, orderBookDataDto, orderBook, i, marketName, quoteMarketName, baseBookEntry, quoteBookEntry);
                }
            }
        }
        return orderBookSpreadDto;
    }

    private static void calculateSpread(List<OrderBookSpreadDto> orderBookSpreadDto, String pair, List<OrderBookDataDto> orderBookDataDto, OrderBookDataDto orderBook, int i, String marketName, String quoteMarketName, List<OrderBookEntryDto> baseBookEntry, List<OrderBookEntryDto> quoteBookEntry) {
        if (baseBookEntry.isEmpty() || quoteBookEntry.isEmpty()) {
            return;
        }
        OrderBookWeightedDto  baseWeighted = OrderBidsAsksWeightedMapper.toOrderBookWeightedDto(baseBookEntry);
        OrderBookWeightedDto quoteWeighted = OrderBidsAsksWeightedMapper.toOrderBookWeightedDto(quoteBookEntry);
        BigDecimal buyPriceFromMarket = baseWeighted.getAvgWeighedPrice();
        BigDecimal buyMarketVolume = baseWeighted.getAvgVolume();
        BigDecimal sellPriceFromMarket = quoteWeighted.getAvgWeighedPrice();
        BigDecimal sellMarketVolume = quoteWeighted.getAvgVolume();
        BigDecimal buyMarketFee = new BigDecimal(orderBook.getFee());
        BigDecimal sellMarketFee = new BigDecimal(orderBookDataDto.get(i).getFee());
        BigDecimal spread = (sellPriceFromMarket
                .divide(buyPriceFromMarket, 6, RoundingMode.HALF_UP))
                .subtract(new BigDecimal(1))
                .multiply(new BigDecimal(100))
                .subtract(buyMarketFee)
                .subtract(sellMarketFee)
                .stripTrailingZeros();
        orderBookSpreadDto.add(new OrderBookSpreadDto(pair, marketName, quoteMarketName, buyPriceFromMarket, sellPriceFromMarket, buyMarketVolume, sellMarketVolume, spread, LocalDateTime.now()));
    }

    private static void fillBaseBookEntry(List<OrderBookEntryDto> baseAsks, BigDecimal baseTargetAskPrice, List<OrderBookEntryDto> baseBookEntry) {
        for (OrderBookEntryDto baseBookEntryPrices : baseAsks) {
            BigDecimal askPrice = baseBookEntryPrices.getPrice();

            if (baseTargetAskPrice.compareTo(askPrice) >= 0) {
                baseBookEntry.add(baseBookEntryPrices);
            } else {
                break;
            }
        }
    }

    private static BigDecimal checkBids(List<OrderBookEntryDto> baseAsks, BigDecimal baseTargetAskPrice, List<OrderBookEntryDto> quoteBookEntry, List<OrderBookEntryDto> quoteBids) {
        for (OrderBookEntryDto quoteBookEntryPrices : quoteBids) {
            BigDecimal buy = baseAsks.get(0).getPrice();
            BigDecimal sellPrice = quoteBookEntryPrices.getPrice();

            if (buy.compareTo(sellPrice) <= 0) {
                quoteBookEntry.add(quoteBookEntryPrices);
            } else {
                break;
            }

        }
        baseTargetAskPrice = getTargetAskPrice(baseTargetAskPrice, quoteBookEntry);
        return baseTargetAskPrice;
    }

    private static BigDecimal getTargetAskPrice(BigDecimal baseTargetAskPrice, List<OrderBookEntryDto> quoteBookEntry) {
        if (!quoteBookEntry.isEmpty()) {
            baseTargetAskPrice = quoteBookEntry.get(0).getPrice();
        }
        return baseTargetAskPrice;
    }

    private static List<OrderBookEntryDto> getBids(OrderBookRequestDto orderBook) {
        return orderBook.getBids()
                .stream()
                .map(OrderBookDtoMapper::toOrderBookEntryDto)
                .toList();
    }

    private static List<OrderBookEntryDto> getAsks(OrderBookRequestDto orderBook) {
        return orderBook.getAsks()
                .stream()
                .map(OrderBookDtoMapper::toOrderBookEntryDto)
                .toList();
    }

    private static OrderBookEntryDto toOrderBookEntryDto(OrderBookEntry orderBookEntry) {
        OrderBookEntryDto orderBookEntryDto = new OrderBookEntryDto();
        orderBookEntryDto.setPrice(new BigDecimal(orderBookEntry.getPrice()));
        orderBookEntryDto.setQty(new BigDecimal(orderBookEntry.getQty()));
        return orderBookEntryDto;
    }

    public static List<OrderBookResponseDto> toOrderBookResponseDto(List<OrderBookSpread> orderBookSpreads) {
        List<OrderBookResponseDto> orderBookSpreadsList = new ArrayList<>();

        for (OrderBookSpread orderBook : orderBookSpreads) {
            orderBookSpreadsList.add(fillOrderBookResponseDto(orderBook));
        }
        return orderBookSpreadsList;
    }

    private static OrderBookResponseDto fillOrderBookResponseDto(OrderBookSpread orderBook) {
        OrderBookResponseDto orderBookResponseDto = new OrderBookResponseDto();

        orderBookResponseDto.setPair(orderBook.getPair());
        orderBookResponseDto.setMarketBaseName(orderBook.getMarketBaseName());
        orderBookResponseDto.setMarketQuoteName(orderBook.getMarketQuoteName());
        orderBookResponseDto.setBasePrice(orderBook.getBasePrice());
        orderBookResponseDto.setQuotePrice(orderBook.getQuotePrice());
        orderBookResponseDto.setBaseVolume(orderBook.getBaseVolume());
        orderBookResponseDto.setQuoteVolume(orderBook.getQuoteVolume());
        orderBookResponseDto.setSpread(orderBook.getSpread());
        orderBookResponseDto.setTime(orderBook.getTime());
        fill25PercentageVolume(orderBookResponseDto, orderBookResponseDto.getBaseVolume(), orderBookResponseDto.getQuoteVolume());

        return orderBookResponseDto;
    }

    public static List<OrderBookSpreadDto> toOrderBookSpreadDto(List<OrderBookSpread> orderBookSpreads) {
        List<OrderBookSpreadDto> orderBookSpreadDtos = new ArrayList<>();

        for (OrderBookSpread orderBookSpread : orderBookSpreads) {
            orderBookSpreadDtos.add(fillOrderBookSpreadDto(orderBookSpread));
        }
        return orderBookSpreadDtos;
    }

    public static List<OrderBookSpread> toOrderBookSpread(List<OrderBookSpreadDto> orderBookSpreadDtos) {
        List<OrderBookSpread> orderBookSpreads = new ArrayList<>();

        for (OrderBookSpreadDto orderBookSpreadDto : orderBookSpreadDtos) {
            orderBookSpreads.add(fillOrderBookSpread(orderBookSpreadDto));
        }
        return orderBookSpreads;
    }

    private static OrderBookSpreadDto fillOrderBookSpreadDto(OrderBookSpread orderBookSpread) {
        OrderBookSpreadDto orderBookSpreadDto = new OrderBookSpreadDto();
        orderBookSpreadDto.setPair(orderBookSpread.getPair());
        orderBookSpreadDto.setMarketBaseName(orderBookSpread.getMarketBaseName());
        orderBookSpreadDto.setMarketQuoteName(orderBookSpread.getMarketQuoteName());
        orderBookSpreadDto.setBasePrice(orderBookSpread.getBasePrice());
        orderBookSpreadDto.setQuotePrice(orderBookSpread.getQuotePrice());
        orderBookSpreadDto.setBaseVolume(orderBookSpread.getBaseVolume());
        orderBookSpreadDto.setQuoteVolume(orderBookSpread.getQuoteVolume());
        orderBookSpreadDto.setSpread(orderBookSpread.getSpread());
        orderBookSpreadDto.setTime(orderBookSpread.getTime());

        return orderBookSpreadDto;
    }

    private static OrderBookSpread fillOrderBookSpread(OrderBookSpreadDto orderBookSpreadDto) {
        OrderBookSpread orderBookSpread = new OrderBookSpread();
        orderBookSpread.setPair(orderBookSpreadDto.getPair());
        orderBookSpread.setMarketBaseName(orderBookSpreadDto.getMarketBaseName());
        orderBookSpread.setMarketQuoteName(orderBookSpreadDto.getMarketQuoteName());
        orderBookSpread.setBasePrice(orderBookSpreadDto.getBasePrice());
        orderBookSpread.setQuotePrice(orderBookSpreadDto.getQuotePrice());
        orderBookSpread.setBaseVolume(orderBookSpreadDto.getBaseVolume());
        orderBookSpread.setQuoteVolume(orderBookSpreadDto.getQuoteVolume());
        orderBookSpread.setSpread(orderBookSpreadDto.getSpread());

        return orderBookSpread;
    }

    private static void fill25PercentageVolume(OrderBookResponseDto orderBookDto, BigDecimal baseVolume, BigDecimal
            quoteVolume) {
        BigDecimal percentageRatio = new BigDecimal("0.25");
        orderBookDto.setQuoteVolume25Percent(quoteVolume.multiply(percentageRatio));
        orderBookDto.setBaseVolume25Percent(baseVolume.multiply(percentageRatio));
    }
}
