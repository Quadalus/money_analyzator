package ru.bikkul.analyzator.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class KlineDataResponseDto {
    @NotEmpty
    private String pair;

    @NotEmpty
    private String marketBaseName;

    @NotEmpty
    private String marketQuoteName;

    @NotEmpty
    private BigDecimal basePrice;

    @NotEmpty
    private BigDecimal quotePrice;

    @NotEmpty
    private BigDecimal baseVolume;

    @NotEmpty
    private BigDecimal baseVolume25Percent;

    @NotEmpty
    private BigDecimal quoteVolume;

    @NotEmpty
    private BigDecimal quoteVolume25Percent;

    @NotEmpty
    private BigDecimal spread;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime time;
}
