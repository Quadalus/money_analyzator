package ru.bikkul.parser.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.bikkul.parser.dto.KlineFullDataDTO;
import ru.bikkul.parser.service.PhemexParserService;

import java.util.Map;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping("/phemex/parser")
@RequiredArgsConstructor
public class PhemexController {
    private final PhemexParserService parserService;

    @GetMapping("/klines")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, KlineFullDataDTO> getKline(@RequestParam Set<String> pairs) {
        return parserService.getKlineForFiveMin(pairs);
    }
}