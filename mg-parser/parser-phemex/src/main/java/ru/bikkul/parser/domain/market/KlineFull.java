package ru.bikkul.parser.domain.market;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@JsonIgnoreProperties(ignoreUnknown = true)
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class KlineFull {
    private String code;
    private String msg;
    private KlineResult data;
}
