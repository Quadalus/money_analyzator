package ru.bikkul.parser.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import ru.bikkul.parser.domain.coin.CoinInfo;
import ru.bikkul.parser.domain.market.KlineFull;
import ru.bikkul.parser.utils.BybitApiProvider;
import ru.bikkul.parser.utils.SignatureGenerator;

import java.time.Instant;

@Service
public class BybitClientImpl implements BybitClient {
    private final WebClient webClient;
    private final BybitApiProvider apiProvider;
    private final String KLINE_URI;
    private final String COIN_INFO_URI;
    private final String KEY_HEADER = "X-BAPI-API-KEY";
    private final String SIGN_HEADER = "X-BAPI-SIGN";
    private final String TIMESTAMP_HEADER = "X-BAPI-TIMESTAMP";
    private final String RECV_HEADER = "X-BAPI-RECV-WINDOW";


    public BybitClientImpl(@Value("${bybit.api.base_url}") String url,
                           @Value("${bybit.api.kline_uri}") String klineUri,
                           @Value("${bybit.api.coin_info_uri}") String coinInfoUri,
                           BybitApiProvider apiProvider) {
        this.webClient = WebClient.builder()
                .baseUrl(url)
                .exchangeStrategies(
                        ExchangeStrategies.builder().codecs(
                                configurer -> configurer.defaultCodecs().maxInMemorySize(1000 * 1024)).build()
                )
                .build();
        this.KLINE_URI = klineUri;
        this.COIN_INFO_URI = coinInfoUri;
        this.apiProvider = apiProvider;
    }

    @Override
    public KlineFull getKline(String symbol, String interval, Integer limit, long startTime, long endTime) {
        return webClient
                .get()
                .uri(uriBuilder -> uriBuilder.path(KLINE_URI)
                        .queryParam("category", "spot")
                        .queryParam("symbol", symbol)
                        .queryParam("interval", interval)
                        .queryParam("limit", limit)
                        .queryParam("start", startTime)
                        .queryParam("end", endTime)
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(KlineFull.class)
                .block();
    }

    @Override
    public CoinInfo getCoinsInformation() {
        Long timestamp = Instant.now().toEpochMilli();
        String recv = "50000";
        String param = "";
        String query = String.format("%s%s%s%s", timestamp, apiProvider.getApiKey(), recv, param);
        String signature = SignatureGenerator.generateBase64(query, apiProvider.getApiSecret());

        return webClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path(COIN_INFO_URI)
                        .build())
                .header(KEY_HEADER, apiProvider.getApiKey())
                .header(SIGN_HEADER, signature)
                .header(TIMESTAMP_HEADER, timestamp.toString())
                .header(RECV_HEADER, recv)
                .retrieve()
                .bodyToMono(CoinInfo.class)
                .block();
    }
}
