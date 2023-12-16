package ru.bikkul.utils.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import ru.bikkul.dto.coin.NetworkInfoDto;

import java.io.IOException;

public class NetworkInfoSerializer extends JsonSerializer<NetworkInfoDto> {
    @Override
    public void serialize(NetworkInfoDto networkInfo, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartArray();
        gen.writeString(networkInfo.getMarketName());
        gen.writeString(networkInfo.getCoin());
        gen.writeString(networkInfo.getName());
        gen.writeString(networkInfo.getWithdrawFee());
        gen.writeBoolean(networkInfo.getDepositEnable());
        gen.writeBoolean(networkInfo.getWithdrawEnable());
        gen.writeEndArray();
    }
}
