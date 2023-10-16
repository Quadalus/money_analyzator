package ru.bikkul.analyzator.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.bikkul.analyzator.dto.KlineDataDto;
import ru.bikkul.analyzator.dto.KlineDataRequestDto;
import ru.bikkul.analyzator.dto.OrderBookRequestDto;
import ru.bikkul.analyzator.dto.OrderBookSpreadDto;
import ru.bikkul.analyzator.service.AnalyzerService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("${analyzer.api.prefix}" + "/analyzer")
@RequiredArgsConstructor
public class AnalyzerController {
    private final AnalyzerService analyzerService;

    @PostMapping("/klines")
    public List<KlineDataDto> saveKlinesData(@RequestBody Map<String, List<KlineDataRequestDto>> klinesData) {
        Long start = System.currentTimeMillis();
        List<KlineDataDto> savedKlinesData = analyzerService.saveKlinesData(klinesData);
        Long end = System.currentTimeMillis();
        Long time = end - start;
        log.info("klines spread data has been saved, klines spread size:{}",  savedKlinesData.size());
        log.info("for ms:{}", time);
        return savedKlinesData;
    }

    @PostMapping("/depth")
    public List<OrderBookSpreadDto> saveOrderBookData(@RequestBody Map<String, List<OrderBookRequestDto>> orderBookData) {
        Long start = System.currentTimeMillis();
        List<OrderBookSpreadDto> savedOrderBookData = analyzerService.saveOrderBookData(orderBookData);
        Long end = System.currentTimeMillis();
        Long time = end - start;
        log.info("order book spread data has been saved, order book spread  size:{}",  savedOrderBookData.size());
        log.info("for ms:{}", time);
        return savedOrderBookData;
    }

    @GetMapping("/depth")
    public String getOrder() {
        return "it's works";
    }

    @PostMapping("/spread")
    public BigDecimal setSpreadTarget(String spreadTarget) {
        return analyzerService.setSpreadTarget(spreadTarget);
    }

    @GetMapping("/spread")
    public BigDecimal getSpreadTarget() {
        return analyzerService.getSpreadTarget();
    }
}
