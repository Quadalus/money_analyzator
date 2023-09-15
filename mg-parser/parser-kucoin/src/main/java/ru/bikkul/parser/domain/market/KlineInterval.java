package ru.bikkul.parser.domain.market;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Kline/Candlestick intervals.
 * m -> minutes; h -> hours; d -> days; w -> weeks; M -> months
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public enum KlineInterval {
  ONE_MINUTE("1min"),
  THREE_MINUTES("3min"),
  FIVE_MINUTES("5min"),
  FIFTEEN_MINUTES("15min"),
  HALF_HOURLY("30min");

  private final String intervalId;

  KlineInterval(String intervalId) {
    this.intervalId = intervalId;
  }

  public String getIntervalId() {
    return intervalId;
  }
}
