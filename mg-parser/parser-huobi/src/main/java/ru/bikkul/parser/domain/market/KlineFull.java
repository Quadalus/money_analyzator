package ru.bikkul.parser.domain.market;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class KlineFull {
    private String status;
    private List<Kline> data;
}
