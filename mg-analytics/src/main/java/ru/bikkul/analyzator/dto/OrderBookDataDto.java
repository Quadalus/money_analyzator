package ru.bikkul.analyzator.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class OrderBookDataDto {
    private String marketName;
    private String marketType;
    private List<OrderBookEntryDto> bids;
    private List<OrderBookEntryDto> asks;
    private String fee;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime time;
}
