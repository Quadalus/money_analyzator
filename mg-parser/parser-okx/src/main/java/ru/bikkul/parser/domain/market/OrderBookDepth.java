package ru.bikkul.parser.domain.market;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class OrderBookDepth {
    private String code;
    private String msg;
    private List<OrderBook> data;
}
