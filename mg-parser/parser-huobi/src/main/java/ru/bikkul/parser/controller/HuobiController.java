package ru.bikkul.parser.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.bikkul.parser.dto.KlineFullDataDTO;
import ru.bikkul.parser.service.HuobiParserService;

import java.util.Map;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping("/huobi/parser")
@RequiredArgsConstructor
public class HuobiController {
    private final HuobiParserService parserService;

    @GetMapping("/klines")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, KlineFullDataDTO> getKline(@RequestParam Set<String> pairs) {
        return parserService.getKlineForFiveMin(pairs);
    }
}