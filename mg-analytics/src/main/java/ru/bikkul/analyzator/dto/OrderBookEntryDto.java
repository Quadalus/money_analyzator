package ru.bikkul.analyzator.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
import ru.bikkul.analyzator.utils.json.OrderBookEntryDeserializer;
import ru.bikkul.analyzator.utils.json.OrderBookEntrySerializer;

import java.math.BigDecimal;

/**
 * An order book entry consisting of price and quantity.
 */
@JsonDeserialize(using = OrderBookEntryDeserializer.class)
@JsonSerialize(using = OrderBookEntrySerializer.class)
@JsonIgnoreProperties(ignoreUnknown = true)
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class OrderBookEntryDto {
    private BigDecimal price;
    private BigDecimal qty;
}
