package ru.bikkul.parser.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import ru.bikkul.parser.domain.coin.CoinInfo;
import ru.bikkul.parser.domain.market.KlineFull;
import ru.bikkul.parser.utils.OkxApiProvider;
import ru.bikkul.parser.utils.SignatureGenerator;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class OkxClientImpl implements OkxClient {
    private final WebClient webClient;
    private final OkxApiProvider apiProvider;
    private final String KLINE_URI;
    private final String KEY_HEADER = "OK-ACCESS-KEY";
    private final String SIGN_HEADER = "OK-ACCESS-SIGN";
    private final String TIMESTAMP_HEADER = "OK-ACCESS-TIMESTAMP";
    private final String PASSPHRASE_HEADER = "OK-ACCESS-PASSPHRASE";
    private final String COIN_INFO_URI;


    public OkxClientImpl(@Value("${okx.api.base_url}") String url,
                         @Value("${okx.api.kline_uri}") String klineUri,
                         @Value("${okx.api.coin_info_uri}") String coinInfoUri,
                         OkxApiProvider apiProvider) {
        this.webClient = WebClient.create(url);
        this.KLINE_URI = klineUri;
        this.COIN_INFO_URI = coinInfoUri;
        this.apiProvider = apiProvider;
    }

    public KlineFull getKline(String symbol, String interval, Integer limit, long startTime, long endTime) {
        return webClient
                .get()
                .uri(uriBuilder -> uriBuilder.path(KLINE_URI)
                        .queryParam("instType", "SPOT".toUpperCase())
                        .queryParam("instId", symbol)
                        .queryParam("bar", interval)
                        .queryParam("limit", limit)
                        .queryParam("before", startTime)
                        .queryParam("after", endTime)
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(KlineFull.class)
                .block();
    }

    @Override
    public List<CoinInfo> getCoinsInformation() {
        String timestamp = Instant.now().atZone(ZoneOffset.UTC).format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));
        String method = "GET";
        String query = String.format("%s%s%s", timestamp, method, COIN_INFO_URI);
        String signature = SignatureGenerator.generateBase64(query, apiProvider.getApiSecret());

        return webClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path(COIN_INFO_URI)
                        .build())
                .header(KEY_HEADER, apiProvider.getApiKey())
                .header(SIGN_HEADER, signature)
                .header(PASSPHRASE_HEADER, apiProvider.getApiPassphrase())
                .header(TIMESTAMP_HEADER, timestamp)
                .retrieve()
                .bodyToFlux(new ParameterizedTypeReference<CoinInfo>() {
                })
                .collectList()
                .block();
    }
}

