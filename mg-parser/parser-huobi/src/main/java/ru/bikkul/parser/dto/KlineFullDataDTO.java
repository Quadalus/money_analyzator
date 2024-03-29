package ru.bikkul.parser.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class KlineFullDataDTO {
    private String marketName = "huobi";
    private String marketType = "cex";

    @NotEmpty
    @PositiveOrZero
    private BigDecimal avgWeighedPrice;

    @NotEmpty
    @PositiveOrZero
    private BigDecimal avgVolume;
    private String fee = "0.2";

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime time = LocalDateTime.now();

}
