package ru.bikkul.parser.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.bikkul.parser.dto.KlineFullDataDto;
import ru.bikkul.parser.service.LbankParserService;

import java.util.Map;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping("${lbank.api.prefix}" + "/parser")
@RequiredArgsConstructor
public class LbankController {
    private final LbankParserService parserService;

    @GetMapping("/klines")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, KlineFullDataDto> getKline(@RequestParam Set<String> pairs) {
        return parserService.getKlineForFiveMin(pairs);
    }
}
