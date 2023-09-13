package ru.bikkul.parser.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BidDto {
    private String market = "binance";
    private String pair;
    private String price;
    private String quantity;
    private LocalDateTime time;
}


