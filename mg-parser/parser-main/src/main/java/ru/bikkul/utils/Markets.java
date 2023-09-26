package ru.bikkul.utils;

public enum Markets {
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
    KRAKEN("8070"),
    BITGET("8071"),
    PROBIT("8073"),
    WOOX("8074");

    private final String port;

    Markets(String port) {
        this.port = port;
    }

    public String getPort() {
        return port;
    }
}
