package ru.bikkul.parser.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import ru.bikkul.parser.domain.market.Kline;

import java.io.IOException;

/**
 * Custom deserializer for an OrderBookEntry, since the API returns an array in the format [ price, qty, [] ].
 */
public class KlineDeserializer extends JsonDeserializer<Kline> {

    @Override
    public Kline deserialize(JsonParser jp, DeserializationContext ctx) throws IOException {
        ObjectCodec oc = jp.getCodec();
        JsonNode node = oc.readTree(jp);
        final Long openTime = node.get(0).asLong();
        final String open = node.get(3).asText();
        final String high = node.get(4).asText();
        final String low = node.get(5).asText();
        final String close = node.get(6).asText();
        final String volume = node.get(7).asText();


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
