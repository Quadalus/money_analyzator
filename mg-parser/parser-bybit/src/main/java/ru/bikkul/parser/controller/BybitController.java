package ru.bikkul.parser.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.bikkul.parser.dto.KlineFullDataDTO;
import ru.bikkul.parser.service.BybitParserService;

import java.util.Map;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping("/bybit/parser")
@RequiredArgsConstructor
public class BybitController {
    private final BybitParserService parserService;

    @GetMapping("/klines")
    public Map<String, KlineFullDataDTO> getKline(@RequestParam Set<String> pairs) {
        return parserService.getKlineForFiveMin(pairs);
    }
}
