package ru.bikkul.parser.domain.market;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Kline/Candlestick intervals.
 * m -> minutes; h -> hours; d -> days; w -> weeks; M -> months
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public enum KlineInterval {
  ONE_MINUTE("minute1"),
  FIVE_MINUTES("minute5"),
  FIFTEEN_MINUTES("minute15"),
  HALF_HOURLY("minute30"),
  HOURLY("hour1");

  private final String intervalId;

  KlineInterval(String intervalId) {
    this.intervalId = intervalId;
  }

  public String getIntervalId() {
    return intervalId;
  }
}
