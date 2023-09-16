package ru.bikkul.parser.domain.market;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Kline/Candlestick intervals.
 * m -> minutes; h -> hours; d -> days; w -> weeks; M -> months
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public enum KlineInterval {
  ONE_MINUTE("1m"),
  FIVE_MINUTES("5m"),
  FIFTEEN_MINUTES("15m"),
  HALF_HOURLY("30m"),
  HOURLY("1h");

  private final String intervalId;

  KlineInterval(String intervalId) {
    this.intervalId = intervalId;
  }

  public String getIntervalId() {
    return intervalId;
  }
}
