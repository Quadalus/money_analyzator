package ru.bikkul.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.bikkul.dto.coin.MarketCoinInfoDto;
import ru.bikkul.service.ParserCoinInfoService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("${parser.api.base.prefix}" + "/parser")
@RequiredArgsConstructor
public class ParserCoinInfoController {
    private final ParserCoinInfoService parserCoinInfoService;

    @GetMapping("/coin/info")
    @ResponseStatus(HttpStatus.OK)
    public List<MarketCoinInfoDto> getAllCoinInfo() {
        List<MarketCoinInfoDto> marketCoins = parserCoinInfoService.getAllCoinInfo();
        log.info("market's coin's info has been got");
        return marketCoins;
    }

    @PostMapping("/coin/info")
    @ResponseStatus(HttpStatus.OK)
    public List<MarketCoinInfoDto> saveAllCoinInfo() {
        List<MarketCoinInfoDto> marketCoinInfoDtos = parserCoinInfoService.saveAllCoinInfo();
        log.info("market's coin's info has been save");
        return marketCoinInfoDtos;
    }
}
