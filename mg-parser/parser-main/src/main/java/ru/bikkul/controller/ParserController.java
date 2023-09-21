package ru.bikkul.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.bikkul.service.ParserService;

import java.util.Set;

@Slf4j
@RestController
@RequestMapping("${parser.api.base.prefix}" + "/parser")
@RequiredArgsConstructor
public class ParserController {
    private final ParserService parserService;

    @GetMapping("/pairs")
    @ResponseStatus(HttpStatus.OK)
    public Set<String> getPairs() {
        Set<String> pairs = parserService.getPairs();
        log.info("pairs has been got, pairs:{}", pairs);
        return pairs;
    }

    @PostMapping("/pair")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public String addPair(@RequestParam String pair) {
        parserService.addPair(pair);
        log.info("add pair to parser, pair:{}", pair);
        return pair;
    }

    @PostMapping("/pairs")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Set<String> addPairs(@RequestParam Set<String> pairs) {
        parserService.setPairs(pairs);
        log.info("add pairs to parser, pairs:{}", pairs);
        return pairs;
    }

    @DeleteMapping("/pair")
    public void deletePair(@RequestParam String pair) {
        parserService.deletePair(pair);
        log.info("delete pair from parser, pair:{}", pair);
    }

    @DeleteMapping("/pairs")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePairs(@RequestParam Set<String> pairs) {
        parserService.deletePairs(pairs);
        log.info("delete pairs from parser, pairs:{}", pairs);
    }

    @DeleteMapping("/pairs/all")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAllPairs() {
        parserService.deleteAllPairs();
        log.info("delete all pairs from parser");
    }
}
