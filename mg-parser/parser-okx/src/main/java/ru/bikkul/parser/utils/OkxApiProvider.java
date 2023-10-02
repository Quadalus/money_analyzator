package ru.bikkul.parser.utils;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class OkxApiProvider {
    @Value("${okx.api.key}")
    private String apiKey;

    @Value("${okx.api.secret}")
    private String apiSecret;

    @Value("${okx.api.passphrase}")
    private String apiPassphrase;
}
