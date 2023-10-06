package ru.bikkul.parser.domain.coin;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Setter
@Getter
@ToString
public class Chains {
    private String currency;
    private List<NetworkInfo> chains;
}
