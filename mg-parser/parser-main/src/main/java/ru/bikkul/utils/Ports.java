package ru.bikkul.utils;

/*    binance:
      port:
    bybit:
      port: 8082
    bingx:
      port: 8087
    mexc:
      port: 8081
    okx:
      port: 8083
    huobi:
      port: 8085
    kucoin:
      port: 8084
    phemex:
      port: 8086
    gate:
      port: 8089
    lbank:
      port: 8088*/
public enum Ports {
    BINANCE("8080"),
    MEXC("8081"),
    BYBIT("8082"),
    OKX("8083"),
    KUCOIN("8084"),
    HUOBI("8085"),
    PHEMEX("8086"),
    BINGX("8087"),
    LBANK("8088"),
    GATE("8089"),
    ANALYZER("8091");

    private final String port;

    Ports(String port) {
        this.port = port;
    }

    public String getPort() {
        return port;
    }
}
