package ru.bikkul.parser.domain.market;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Kline/Candlestick intervals.
 * m -> minutes; h -> hours; d -> days; w -> weeks; M -> months
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public enum KlineInterval {
  ONE_MINUTE("60"),
  FIVE_MINUTES("300"),
  FIFTEEN_MINUTES("900"),
  HALF_HOURLY("1800"),
  HOURLY("3600");

  private final String intervalId;

  KlineInterval(String intervalId) {
    this.intervalId = intervalId;
  }

  public String getIntervalId() {
    return intervalId;
  }
}
