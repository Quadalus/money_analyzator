package ru.bikkul.parser.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.bikkul.parser.domain.market.OrderBookEntry;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderBookDto {
    private String marketName = "mexc";
    private String marketType = "cex";
    private List<OrderBookEntry> bids;
    private List<OrderBookEntry> asks;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime time = LocalDateTime.now();
}
