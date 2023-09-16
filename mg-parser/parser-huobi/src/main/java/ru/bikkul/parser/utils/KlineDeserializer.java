package ru.bikkul.parser.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import ru.bikkul.parser.domain.market.Kline;

import java.io.IOException;

/**
 "id": 1629769200,
 "open": 49056.37,
 "close": 49025.51,
 "low": 49022.86,
 "high": 49056.38,
 "amount": 3.946281917950917,
 "vol": 193489.67275732,
 "count": 196
 */
public class KlineDeserializer extends JsonDeserializer<Kline> {

    @Override
    public Kline deserialize(JsonParser jp, DeserializationContext ctx) throws IOException {
        ObjectCodec oc = jp.getCodec();
        JsonNode node = oc.readTree(jp);
        final Long openTime = node.get("id").asLong();
        final String open = node.get("open").asText();
        final String close = node.get("close").asText();
        final String high = node.get("high").asText();
        final String low = node.get("low").asText();
        final String volume = node.get("amount").asText();

        Kline kline = new Kline();
        kline.setOpenTime(openTime);
        kline.setOpen(open);
        kline.setHigh(high);
        kline.setLow(low);
        kline.setClose(close);
        kline.setVolume(volume);
        return kline;
    }
}
