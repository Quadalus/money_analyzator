package ru.bikkul.parser.domain.market;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;
import ru.bikkul.parser.utils.KlineResultDeserializer;

@JsonIgnoreProperties(ignoreUnknown = true)
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonDeserialize(using = KlineResultDeserializer.class)
public class KlineResult {
    private String data;
}
