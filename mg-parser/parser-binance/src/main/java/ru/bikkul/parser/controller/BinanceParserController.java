package ru.bikkul.parser.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.bikkul.parser.domain.coin.CoinInfo;
import ru.bikkul.parser.dto.KlineFullDataDto;
import ru.bikkul.parser.dto.OrderBookDto;
import ru.bikkul.parser.service.BinanceParserService;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("${binance.api.prefix}" + "/parser")
public class BinanceParserController {
    private final BinanceParserService binanceParserService;

    @GetMapping("/klines")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, KlineFullDataDto> parseKline(@RequestParam Set<String> pairs) {
        Map<String, KlineFullDataDto> binanceKline = binanceParserService.getKlineForFiveMin(pairs);
        log.info("kline for four minutes has been got:{}", binanceKline);
        return binanceKline;
    }

    @GetMapping("/coin/info")
    @ResponseStatus(HttpStatus.OK)
    public List<CoinInfo> getCoinsInfo() {
        List<CoinInfo> coinsInformation = binanceParserService.getCoinsInformation();
        log.info("coin's info has been got, coin info size:{}", coinsInformation.size());
        return coinsInformation;
    }

    @GetMapping("/depth")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, OrderBookDto> parseOrderBook(@RequestParam Set<String> pairs) {
        Map<String, OrderBookDto> orderBook = binanceParserService.getSpotData(pairs);
        log.info("order book has been got:{}", orderBook);
        return orderBook;
    }
}
