package ru.bikkul.parser.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.bikkul.parser.service.BinanceParserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/binance")
public class BinanceParserController {
    private final BinanceParserService binanceParserService;

    /*@GetMapping("/parse")
    public void parseBinance() {
        binanceParserService.init();
    }*/
}
