package ru.bikkul.parser.utils;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class BybitApiProvider {
    @Value("${bybit.api.key}")
    private String apiKey;

    @Value("${bybit.api.secret}")
    private String apiSecret;
}
