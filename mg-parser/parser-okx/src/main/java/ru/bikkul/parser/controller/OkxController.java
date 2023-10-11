package ru.bikkul.parser.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.bikkul.parser.dto.CoinInfoDto;
import ru.bikkul.parser.dto.KlineFullDataDTO;
import ru.bikkul.parser.dto.OrderBookDto;
import ru.bikkul.parser.service.OkxParserService;

import java.util.List;
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

    @GetMapping("/coin/info")
    @ResponseStatus(HttpStatus.OK)
    public List<CoinInfoDto> getCoinsInfo() {
        List<CoinInfoDto> coinsInformation = parserService.getCoinsInformation();
        log.info("coin's info has been got, coin info size:{}", coinsInformation.size());
        return coinsInformation;
    }

    @GetMapping("/depth")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, OrderBookDto> parseOrderBook(@RequestParam Set<String> pairs) {
        Map<String, OrderBookDto> orderBook = parserService.getSpotData(pairs);
        log.info("order book has been got:{}", orderBook);
        return orderBook;
    }
}
