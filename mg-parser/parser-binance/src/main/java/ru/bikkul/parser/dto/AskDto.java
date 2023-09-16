package ru.bikkul.parser.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AskDto {
    private String market = "binance";
    private String pair;
    private String price;
    private String quantity;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
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
