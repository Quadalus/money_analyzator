package ru.bikkul.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
import ru.bikkul.utils.json.OrderBookEntryDeserializer;
import ru.bikkul.utils.json.OrderBookEntrySerializer;

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
public class OrderBookEntry {
    private String price;
    private String qty;
}
