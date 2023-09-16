package ru.bikkul.parser.domain.market;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public enum KlineInterval {
  ONE_MINUTE("1"),
  FIVE_MINUTES("5"),
  FIFTEEN_MINUTES("15"),
  HALF_HOURLY("60"),
  HOURLY("60");

  private final String intervalId;

  KlineInterval(String intervalId) {
    this.intervalId = intervalId;
  }

  public String getIntervalId() {
    return intervalId;
  }
}
