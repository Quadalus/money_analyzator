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
        List<KlineDataDto> savedKlinesData = analyzerService.saveKlinesData(klinesData);
        log.info("klines spread data has been saved, klines spread size:{}",  savedKlinesData.size());
        return savedKlinesData;
    }

    @PostMapping("/depth")
    public List<OrderBookSpreadDto> saveOrderBookData(@RequestBody Map<String, List<OrderBookRequestDto>> orderBookData) {
        List<OrderBookSpreadDto> savedOrderBookData = analyzerService.saveOrderBookData(orderBookData);
        log.info("order book spread data has been saved, order book spread  size:{}",  savedOrderBookData.size());
        return savedOrderBookData;
    }

    @GetMapping("/getKlineData")
    public List<String> getKlineData() {
        log.info("kline data for 1 min has been got!");
        return analyzerService.getKlineData();
    }

    @GetMapping("/getOrderData")
    public List<String> getOrderData() {
        log.info("order book data for 1 min has been got!");
        return analyzerService.getOrderData();
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
