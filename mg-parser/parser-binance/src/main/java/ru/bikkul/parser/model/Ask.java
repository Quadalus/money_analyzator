package ru.bikkul.parser.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Ask {
    private String market = "binance";
    private String pair;
    private String price;
    private String quantity;
    private LocalDateTime time;

    @Override
    public String toString() {
        return """
                market: %s
                pair: %s
                price: %s
                quantity: %s
                time: %s
                """.formatted(market,pair, price, quantity, time);
    }
}
