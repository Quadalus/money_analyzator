package ru.bikkul.parser.utils.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import ru.bikkul.parser.domain.coin.NetworkInfo;

import java.io.IOException;

public class NetworkInfoDeserializer extends JsonDeserializer<NetworkInfo> {
    @Override
    public NetworkInfo deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        ObjectCodec oc = jsonParser.getCodec();
        JsonNode node = oc.readTree(jsonParser);
        final String coinName = node.get("ccy").asText();
        final String network = node.get("chain").asText();
        final String fee = node.get("minFee").asText();
        final Boolean isDepositEnable = node.get("canDep").asBoolean();
        final Boolean isWithdrawEnable = node.get("canWd").asBoolean();

        NetworkInfo networkInfo = new NetworkInfo();
        String marketName = "okx";
        networkInfo.setMarketName(marketName);
        networkInfo.setCoin(coinName);
        networkInfo.setName(network);
        networkInfo.setNetworkFee(fee);
        networkInfo.setDepositEnable(isDepositEnable);
        networkInfo.setWithdrawEnable(isWithdrawEnable);
        return networkInfo;
    }
}
