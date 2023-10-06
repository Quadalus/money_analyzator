package ru.bikkul.parser.utils;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class MexcApiProvider {
    @Value("${mexc.api.key}")
    private String apiKey;

    @Value("${mexc.api.secret}")
    private String apiSecret;
}
