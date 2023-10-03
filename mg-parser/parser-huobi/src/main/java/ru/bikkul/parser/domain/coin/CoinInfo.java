package ru.bikkul.parser.domain.coin;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Setter
@Getter
public class CoinInfo {
    private String code;
    private List<Chains> data;
}
