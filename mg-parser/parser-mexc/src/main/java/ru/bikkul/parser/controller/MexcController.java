package ru.bikkul.parser.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.bikkul.parser.domain.coin.CoinInfo;
import ru.bikkul.parser.dto.KlineFullDataDto;
import ru.bikkul.parser.service.MexcParserService;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping("${mexc.api.prefix}" + "/parser")
@RequiredArgsConstructor
public class MexcController {
    private final MexcParserService parserService;

    @GetMapping("/klines")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, KlineFullDataDto> getKline(@RequestParam Set<String> pairs) {
        Map<String, KlineFullDataDto> klineForFourMin = parserService.getKlineForFourMin(pairs);
        log.info("klines for four min has been got, klines pair:{}", klineForFourMin.keySet());
        return klineForFourMin;
    }

    @GetMapping("/coin/info")
    @ResponseStatus(HttpStatus.OK)
    public List<CoinInfo> getCoinsInfo() {
        List<CoinInfo> coinsInformation = parserService.getCoinsInformation();
        log.info("coin's info has been got, coin info size:{}", coinsInformation.size());
        return coinsInformation;
    }
}
