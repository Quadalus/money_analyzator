package ru.bikkul.parser.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.bikkul.parser.dto.AvgWeightedKlineDto;
import ru.bikkul.parser.service.BinanceParserService;

import java.util.Map;
import java.util.Set;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/binance/parse")
public class BinanceParserController {
    private final BinanceParserService binanceParserService;

    @GetMapping("/kline")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, AvgWeightedKlineDto> parseKline(@RequestParam Set<String> pairs) {
        Map<String, AvgWeightedKlineDto> binanceKline = binanceParserService.getKlineForFiveMin(pairs);
        log.info("kline for five minutes has been got:{}", binanceKline);
        return binanceKline;
    }

    /*@GetMapping("/spot")
    public Map<String, AvgWeightedKlinePriceDto> parseSpot(@RequestParam Set<String> pairs) {
        return binanceParserService.getKlineForFiveMin(pairs);
    }*/
}