package ru.bikkul.analyzator.service.impl;

import ch.qos.logback.core.testUtil.StringListAppender;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.bikkul.analyzator.dto.*;
import ru.bikkul.analyzator.model.KlineSpread;
import ru.bikkul.analyzator.model.OrderBookSpread;
import ru.bikkul.analyzator.repository.KlineSpreadRepository;
import ru.bikkul.analyzator.repository.OrderBookSpreadRepository;
import ru.bikkul.analyzator.service.AnalyzerService;
import ru.bikkul.analyzator.utils.mapper.KlineDataDtoMapper;
import ru.bikkul.analyzator.utils.mapper.OrderBookDtoMapper;

import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@EnableScheduling
@RequiredArgsConstructor
public class AnalyzerServiceFileImpl implements AnalyzerService {
    private final KlineSpreadRepository klineSpreadRepository;
    private final OrderBookSpreadRepository orderBookSpreadRepository;
    private BigDecimal spreadTarget = new BigDecimal("0.1");

    @Override
    public List<KlineDataDto> saveKlinesData(Map<String, List<KlineDataRequestDto>> klinesData) {
        List<KlineDataDto> klinesDataDto = KlineDataDtoMapper.toKlinesDataDto(klinesData);
        List<KlineSpread> klineSpreads = KlineDataDtoMapper.toKlineSpread(klinesDataDto);
        List<KlineSpread> onlyPlusSpread = klinesOnlyPositiveSpread(klineSpreads);
        List<KlineSpread> savedKlineSpreads = klineSpreadRepository.saveAll(onlyPlusSpread);
        LocalDateTime start = LocalDateTime.now().minusMinutes(1);
        printKlineSpeads(KlineDataDtoMapper.toKlinesDataResponseDto(klineSpreadRepository.searchKlineSpreadBySpreadIsGreaterThanAndTimeIsAfter(spreadTarget, start)));
        return KlineDataDtoMapper.toKlinesDataDto(savedKlineSpreads);
    }

    @Override
    public List<OrderBookSpreadDto> saveOrderBookData(Map<String, List<OrderBookRequestDto>> ordersResponse) {
        Map<String, List<OrderBookDataDto>> orderBookDto = OrderBookDtoMapper.toOrderBookDataDto(ordersResponse);
        List<OrderBookSpreadDto> orderBookSpreadDto = OrderBookDtoMapper.toOrderBookWeightedDto(orderBookDto);
        List<OrderBookSpread> orderBookSpreads = OrderBookDtoMapper.toOrderBookSpread(orderBookSpreadDto);
        List<OrderBookSpread> onlyPlusSpread = orderBookOnlyPositiveSpread(orderBookSpreads);
        List<OrderBookSpread> savedOrderBookSpread = orderBookSpreadRepository.saveAll(onlyPlusSpread);
        LocalDateTime start = LocalDateTime.now().minusMinutes(1);
        printOrderBookSpreads(OrderBookDtoMapper.toOrderBookResponseDto(orderBookSpreadRepository.searchOrderBookSpreadBySpreadIsGreaterThanAndTimeIsAfter(spreadTarget, start)));
        return OrderBookDtoMapper.toOrderBookSpreadDto(savedOrderBookSpread);
    }


    @Override
    public List<String> getOrderData() {
        LocalDateTime start = LocalDateTime.now().minusMinutes(1);
        return orderBookToString(OrderBookDtoMapper.toOrderBookResponseDto(orderBookSpreadRepository.searchOrderBookSpreadBySpreadIsGreaterThanAndTimeIsAfter(spreadTarget, start)));
    }

    @Override
    public List<String> getKlineData() {
        LocalDateTime start = LocalDateTime.now().minusMinutes(1);
        return klinesToString(KlineDataDtoMapper.toKlinesDataResponseDto(klineSpreadRepository.searchKlineSpreadBySpreadIsGreaterThanAndTimeIsAfter(spreadTarget, start)));
    }

    @Override
    public BigDecimal setSpreadTarget(String spreadTarget) {
        this.spreadTarget = new BigDecimal(spreadTarget);
        return this.spreadTarget;
    }

    @Override
    public BigDecimal getSpreadTarget() {
        return this.spreadTarget;
    }

    @Scheduled(fixedDelay = 900000)
    private void clearKlineFile() {
        Path path = Path.of(".\\kline_spreads.txt");
        try {
            FileWriter fw = new FileWriter(path.toFile(), false);
            fw.write("");
            fw.close();
            log.info("kline spreads file clear");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    @Scheduled(fixedDelay = 900000)
    private void clearOrderFile() {
        Path path = Path.of(".\\order_book_spreads.txt");
        try {
            FileWriter fw = new FileWriter(path.toFile(), false);
            fw.write("");
            fw.close();
            log.info("order book spreads file clear");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void printKlineSpeads(List<KlineDataResponseDto> savedKlineSpreads) {
        Path path = Path.of(".\\kline_spreads.txt");
        List<String> spreads = klinesToString(savedKlineSpreads);

        try {
            Files.write(path, spreads, StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private List<String> klinesToString(List<KlineDataResponseDto> savedKlineSpreads) {
        List<String> klinesSpeads = new ArrayList<>();

        for (KlineDataResponseDto kline : savedKlineSpreads) {
            String text = String
                    .format("%s | %s (buy) price=%s volume=%s(%s) --> %s (sell) price=%s volume=%s(%s) | spread=%s | time=%s",
                            kline.getPair(), kline.getMarketBaseName(), kline.getBasePrice(), kline.getBaseVolume(), kline.getBaseVolume25Percent(),
                            kline.getMarketQuoteName(), kline.getQuotePrice(), kline.getQuoteVolume(), kline.getQuoteVolume25Percent(),
                            kline.getSpread(), kline.getTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            klinesSpeads.add(text);
        }
        return klinesSpeads;
    }

    private void printOrderBookSpreads(List<OrderBookResponseDto> savedOrderBookSpreads) {
        Path path = Path.of(".\\order_book_spreads.txt");
        List<String> spreads = orderBookToString(savedOrderBookSpreads);

        try {
            Files.write(path, spreads, StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private List<String> orderBookToString(List<OrderBookResponseDto> savedOrderBookSpreads) {
        List<String> orderBookSpread = new ArrayList<>();

        for (OrderBookResponseDto kline : savedOrderBookSpreads) {
            String text = String
                    .format("%s | %s (buy) price=%s volume=%s(%s) --> %s (sell) price=%s volume=%s(%s) | spread=%s | time=%s",
                            kline.getPair(), kline.getMarketBaseName(), kline.getBasePrice(), kline.getBaseVolume(), kline.getBaseVolume25Percent(),
                            kline.getMarketQuoteName(), kline.getQuotePrice(), kline.getQuoteVolume(), kline.getQuoteVolume25Percent(),
                            kline.getSpread(), kline.getTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            orderBookSpread.add(text);
        }
        return orderBookSpread;
    }

    private List<OrderBookSpread> orderBookOnlyPositiveSpread(List<OrderBookSpread> orderBookSpreads) {
        return orderBookSpreads.stream()
                .filter(book -> book.getSpread().compareTo(new BigDecimal("0")) > 0)
                .collect(Collectors.toList());
    }

    private List<KlineSpread> klinesOnlyPositiveSpread(List<KlineSpread> klines) {
        return klines.stream()
                .filter(kline -> kline.getSpread().compareTo(new BigDecimal("0")) > 0)
                .collect(Collectors.toList());
    }
}
