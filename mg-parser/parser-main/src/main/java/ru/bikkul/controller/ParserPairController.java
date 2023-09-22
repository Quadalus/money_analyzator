package ru.bikkul.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.bikkul.service.ParserPairService;

import java.util.Set;

@Slf4j
@RestController
@RequestMapping("${parser.api.base.prefix}" + "/parser")
@RequiredArgsConstructor
public class ParserPairController {
    private final ParserPairService parserPairService;

    @GetMapping("/pairs")
    @ResponseStatus(HttpStatus.OK)
    public Set<String> getPairs() {
        Set<String> pairs = parserPairService.getPairs();
        log.info("pairs has been got, pairs:{}", pairs);
        return pairs;
    }

    @PostMapping("/pair")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public String addPair(@RequestParam String pair) {
        parserPairService.addPair(pair);
        log.info("added pair to parser, pair:{}", pair);
        return pair;
    }

    @PostMapping("/pairs")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Set<String> addPairs(@RequestParam Set<String> pairs) {
        parserPairService.setPairs(pairs);
        log.info("added pairs to parser, pairs:{}", pairs);
        return pairs;
    }

    @DeleteMapping("/pair")
    public void deletePair(@RequestParam String pair) {
        parserPairService.deletePair(pair);
        log.info("delete pair from parser, pair:{}", pair);
    }

    @DeleteMapping("/pairs")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePairs(@RequestParam Set<String> pairs) {
        parserPairService.deletePairs(pairs);
        log.info("delete pairs from parser, pairs:{}", pairs);
    }

    @DeleteMapping("/pairs/all")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAllPairs() {
        parserPairService.deleteAllPairs();
        log.info("delete all pairs from parser");
    }
}
