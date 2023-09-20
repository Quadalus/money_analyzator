package ru.bikkul.parser.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.bikkul.parser.dto.KlineFullDataDTO;
import ru.bikkul.parser.service.BingxParserService;

import java.util.Map;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping("${bingx.api.prefix}" + "/parser")
@RequiredArgsConstructor
public class BingxController {
    private final BingxParserService parserService;

    @GetMapping("/klines")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, KlineFullDataDTO> getKline(@RequestParam Set<String> pairs) {
        Map<String, KlineFullDataDTO> klineForFiveMin = parserService.getKlineForFourMin(pairs);
        log.info("kline for five minutes has been got:{}", klineForFiveMin.keySet());
        return klineForFiveMin;
    }
}
