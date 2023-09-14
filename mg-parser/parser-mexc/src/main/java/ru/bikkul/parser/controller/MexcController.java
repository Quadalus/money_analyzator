package ru.bikkul.parser.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.bikkul.parser.dto.AvgWeightedKlineDto;
import ru.bikkul.parser.service.MexcParserService;

import java.util.Map;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping("/mexc/parser")
@RequiredArgsConstructor
public class MexcController {
    private final MexcParserService parserService;

    @GetMapping("/klines")
    public Map<String, AvgWeightedKlineDto> getKline(@RequestParam Set<String> pairs) {
        return parserService.getKlineForFiveMin(pairs);
    }
}
