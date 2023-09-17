package ru.bikkul.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class KlineData {
    private String marketName;
    private String marketType;
    private BigDecimal avgWeighedPrice;
    private BigDecimal avgVolume;
    private String fee;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime time;

}
