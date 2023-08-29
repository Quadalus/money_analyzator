package ru.bikkul.parser.controller;

import lombok.NoArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/parser")
public class ParserController {

    @Scheduled(fixedDelay = 300000L)
    @GetMapping("/binance")
    public void getBinanceValues() {
        System.out.println("Do noting");
    }

    @Scheduled(fixedDelay = 300000L)
    @GetMapping("/okx")
    public void getOkxValues() {
        System.out.println("Do noting");
    }

    @Scheduled(fixedDelay = 300000L)
    @GetMapping("/uniswap")
    public void getUniswapValues() {
        System.out.println("Do noting");
    }

    @Scheduled(fixedDelay = 300000L)
    @GetMapping("/kyberswap")
    public void getKyberswapValues() {
        System.out.println("Do noting");
    }

    @Scheduled(fixedDelay = 300000L)
    @GetMapping("/biswap")
    public void getBiswapValues() {
        System.out.println("Do noting");
    }

    @Scheduled(fixedDelay = 300000L)
    @GetMapping("/frax")
    public void getFraxValues() {
        System.out.println("Do noting");
    }
}
