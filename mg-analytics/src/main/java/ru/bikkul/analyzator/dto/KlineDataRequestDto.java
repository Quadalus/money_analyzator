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
public class KlineDataRequestDto {
    @NotEmpty
    private String marketName;

    @NotEmpty
    private String marketType;

    @NotEmpty
    private BigDecimal avgWeighedPrice;

    @NotEmpty
    private BigDecimal avgVolume;

    @NotEmpty
    private String fee;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime time;
}
