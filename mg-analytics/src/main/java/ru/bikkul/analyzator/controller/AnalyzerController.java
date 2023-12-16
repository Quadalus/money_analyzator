package ru.bikkul.analyzator.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
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
    public List<KlineDataDto> saveKlinesData(@RequestBody String klinesData) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        Map<String, List<KlineDataRequestDto>> stringListMap = objectMapper.readValue(klinesData, new TypeReference<Map<String, List<KlineDataRequestDto>>>() {
        });
        List<KlineDataDto> savedKlinesData = analyzerService.saveKlinesData(stringListMap);
        log.info("klines spread data has been saved, klines spread size:{}", savedKlinesData.size());
        return savedKlinesData;
    }

    @PostMapping("/depth")
    public List<OrderBookSpreadDto> saveOrderBookData(@RequestBody String orderBookData) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        Map<String, List<OrderBookRequestDto>> stringListMap = objectMapper.readValue(orderBookData, new TypeReference<Map<String, List<OrderBookRequestDto>>>() {
        });
        List<OrderBookSpreadDto> savedOrderBookData = analyzerService.saveOrderBookData(stringListMap);
        log.info("order book spread data has been saved, order book spread  size:{}", savedOrderBookData.size());
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
