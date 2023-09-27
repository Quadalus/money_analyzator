package ru.bikkul.model;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class NetworkInfoSerializer extends JsonSerializer<NetworkInfo> {
    @Override
    public void serialize(NetworkInfo networkInfo, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartArray();
        gen.writeString(networkInfo.getCoin());
        gen.writeString(networkInfo.getName());
        gen.writeString(networkInfo.getNetwork());
        gen.writeBoolean(networkInfo.getDepositEnable());
        gen.writeBoolean(networkInfo.getWithdrawEnable());
        gen.writeEndArray();
    }
}
