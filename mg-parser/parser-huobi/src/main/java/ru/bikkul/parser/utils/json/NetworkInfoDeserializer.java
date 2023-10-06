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
        final String network = node.get("displayName").asText();
        String fee;
        try {
            fee = node.get("transactFeeWithdraw").asText();
        } catch (Exception e) {
            fee = "0.0";
        }
        final String isDepositEnable = node.get("depositStatus").asText();
        final String isWithdrawEnable = node.get("withdrawStatus").asText();

        NetworkInfo networkInfo = new NetworkInfo();
        String marketName = "huobi";
        networkInfo.setMarketName(marketName);
        networkInfo.setName(network);
        networkInfo.setCoin("undefined");
        networkInfo.setNetworkFee(fee);
        networkInfo.setDepositEnable((isDepositEnable.equals("allowed")));
        networkInfo.setWithdrawEnable(isWithdrawEnable.equals("allowed"));
        return networkInfo;
    }
}
