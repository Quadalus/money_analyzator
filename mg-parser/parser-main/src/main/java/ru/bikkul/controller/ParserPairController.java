package ru.bikkul.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.bikkul.service.ParserPairService;
import ru.bikkul.utils.Markets;

import java.util.Map;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping("${parser.api.base.prefix}" + "/parser")
@RequiredArgsConstructor
public class ParserPairController {
    private final ParserPairService parserPairService;

    @GetMapping("/pairs")
    @ResponseStatus(HttpStatus.OK)
    public Map<Markets, Set<String>> getPairs() {
        Map<Markets, Set<String>> pairs = parserPairService.getPairs();
        log.info("pairs has been got, pairs:{}", pairs);
        return pairs;
    }

    @PostMapping("/pairs")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Map<String, String> addPairs(@RequestParam Map<String, String> marketPairs) {
        parserPairService.setPairs(marketPairs);
        log.info("added marketPairs to parser, marketPairs:{}", marketPairs);
        return marketPairs;
    }

    @DeleteMapping("/pairs")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePairs(@RequestParam Map<String, String> marketPairs) {
        parserPairService.deletePairs(marketPairs);
        log.info("delete marketPairs from parser, marketPairs:{}", marketPairs);
    }

    @DeleteMapping("/pairs/all")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAllPairs() {
        parserPairService.deleteAllPairs();
        log.info("delete all pairs from parser");
    }
}
