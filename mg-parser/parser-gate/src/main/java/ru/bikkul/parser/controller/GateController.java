package ru.bikkul.parser.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.bikkul.parser.dto.KlineFullDataDto;
import ru.bikkul.parser.service.GateParserService;

import java.util.Map;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping("${gate.api.prefix}" + "/parser")
@RequiredArgsConstructor
public class GateController {
    private final GateParserService parserService;

    @GetMapping("/klines")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, KlineFullDataDto> getKline(@RequestParam Set<String> pairs) {
        Map<String, KlineFullDataDto> klineForFourMin = parserService.getKlineForFourMin(pairs);
        log.info("klines for four min has been got, klines pair:{}", klineForFourMin.keySet());
        return klineForFourMin;
    }
}
