package ru.bikkul.parser.domain.market;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class OrderBookDepth {
    private String ch;
    private String status;
    private OrderBook tick;
}
