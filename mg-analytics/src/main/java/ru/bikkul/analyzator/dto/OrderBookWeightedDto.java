package ru.bikkul.analyzator.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.math.BigDecimal;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class OrderBookWeightedDto {
    @NotEmpty
    private BigDecimal avgWeighedPrice;

    @NotEmpty
    private BigDecimal avgVolume;
}
