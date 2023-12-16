package ru.bikkul.utils;

public enum Markets {
    BINANCE("8081"),
    MEXC("8084"),
    BYBIT("8085"),
    OKX("8083"),
    HUOBI("8082");

    private final String port;

    Markets(String port) {
        this.port = port;
    }

    public String getPort() {
        return port;
    }
}
