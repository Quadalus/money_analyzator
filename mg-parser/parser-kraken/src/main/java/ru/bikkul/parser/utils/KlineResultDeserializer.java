package ru.bikkul.parser.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import ru.bikkul.parser.domain.market.KlineResult;

import java.io.IOException;

/**
 * Custom deserializer for an OrderBookEntry, since the API returns an array in the format [ price, qty, [] ].
 */
public class KlineResultDeserializer extends JsonDeserializer<KlineResult> {

    @Override
    public KlineResult deserialize(JsonParser jp, DeserializationContext ctx) throws IOException {
        ObjectCodec oc = jp.getCodec();
        jp.nextToken();
        String fieldName = jp.getCurrentName();
        JsonNode jsonNode = oc.readTree(jp);
        String jsonKline = jsonNode.get(fieldName).toString();
        KlineResult klineResult = new KlineResult();
        klineResult.setData(jsonKline);
        return klineResult;
    }
}