package ru.bikkul.parser.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.bikkul.parser.dto.KlineFullDataDTO;
import ru.bikkul.parser.service.OkxParserService;

import java.util.Map;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping("${okx.api.prefix}" + "/parser")
@RequiredArgsConstructor
public class OkxController {
    private final OkxParserService parserService;

    @GetMapping("/klines")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, KlineFullDataDTO> getKline(@RequestParam Set<String> pairs) {
        Map<String, KlineFullDataDTO> klineForFourMin = parserService.getKlineForFourMin(pairs);
        log.info("klines for four min has been got, klines pair:{}", klineForFourMin.keySet());
        return klineForFourMin;
    }
}
