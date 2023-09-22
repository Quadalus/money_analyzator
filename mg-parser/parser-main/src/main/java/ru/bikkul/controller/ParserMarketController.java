package ru.bikkul.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.bikkul.service.ParserMarketService;
import ru.bikkul.utils.Markets;

import java.util.Set;

@Slf4j
@RestController
@RequestMapping("${parser.api.base.prefix}" + "/parser")
@RequiredArgsConstructor
public class ParserMarketController {
    private final ParserMarketService parserService;

    @DeleteMapping("/market/tracking")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMarketFromTracking(@RequestParam Markets market) {
        parserService.deleteTrackingMarket(market);
        log.info("market has been delete from tracking, market:{}", market);
    }

    @DeleteMapping("/markets/tracking")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMarketsFromTracking(@RequestParam Set<Markets> market) {
        parserService.deleteTrackingMarkets(market);
        log.info("market has been delete from tracking, market:{}", market);
    }

    @DeleteMapping("/market/tracking/all")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAllMarketFromTracking() {
        parserService.deleteAllTrackingMarket();
        log.info("markets has been delete from tracking");
    }

    @GetMapping("/market/tracking")
    @ResponseStatus(HttpStatus.OK)
    public Set<Markets> getTrackingMarkets() {
        Set<Markets> targetingMarkets = parserService.getTrackingMarkets();
        log.info("tracking markets has been got, markets:{}", targetingMarkets);
        return targetingMarkets;
    }

    @GetMapping("/market/available")
    @ResponseStatus(HttpStatus.OK)
    public Markets[] getAvailableMarkets() {
        Markets[] availableMarkets = parserService.getAvailableMarkets();
        log.info("available markets has been got");
        return availableMarkets;
    }

    @PostMapping("/market/tracking")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Markets addMarketToTracking(@RequestParam Markets market) {
        Markets addedMarket = parserService.addMarketToTracking(market);
        log.info("market has been added to tracking, market:{}", addedMarket);
        return addedMarket;
    }

    @PostMapping("/markets/tracking")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Set<Markets> addMarketToTracking(@RequestParam Set<Markets> markets) {
        Set<Markets> addedMarkets = parserService.addMarketsToTracking(markets);
        log.info("markets has been added to tracking, markets:{}", addedMarkets);
        return addedMarkets;
    }
}
