package ru.bikkul.utils.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import ru.bikkul.dto.NetworkInfoDto;

import java.io.IOException;

public class NetworkInfoDeserializer extends JsonDeserializer<NetworkInfoDto> {
    @Override
    public NetworkInfoDto deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        ObjectCodec oc = jsonParser.getCodec();
        JsonNode node = oc.readTree(jsonParser);
        final String marketName = node.get(0).asText();
        final String coinName = node.get(1).asText();
        final String name = node.get(2).asText();
        final Boolean isDepositEnable = node.get(3).asBoolean();
        final Boolean isWithdrawEnable = node.get(4).asBoolean();
        final String withdrawFee = node.get(5).asText();

        NetworkInfoDto networkInfo = new NetworkInfoDto();
        networkInfo.setMarketName(marketName);
        networkInfo.setCoin(coinName);
        networkInfo.setName(name);
        networkInfo.setDepositEnable(isDepositEnable);
        networkInfo.setWithdrawEnable(isWithdrawEnable);
        networkInfo.setWithdrawFee(withdrawFee);
        return networkInfo;
    }
}
