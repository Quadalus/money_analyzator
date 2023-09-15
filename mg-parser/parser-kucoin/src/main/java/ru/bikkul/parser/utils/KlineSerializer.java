package ru.bikkul.parser.utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import ru.bikkul.parser.domain.market.Kline;

import java.io.IOException;

/**
 * Custom serializer for an OrderBookEntry.
 */
public class KlineSerializer extends JsonSerializer<Kline> {

  @Override
  public void serialize(Kline kline, JsonGenerator gen, SerializerProvider serializers) throws IOException {
    gen.writeStartArray();
    gen.writeNumber(kline.getOpenTime());
    gen.writeString(kline.getOpen());
    gen.writeString(kline.getClose());
    gen.writeString(kline.getHigh());
    gen.writeString(kline.getLow());
    gen.writeString(kline.getVolume());
    gen.writeString(kline.getQuoteAssetVolume());
    gen.writeEndArray();
  }
}
