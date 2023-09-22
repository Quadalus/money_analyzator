package ru.bikkul.parser.client;

public interface KrakenClient {
    String getKline(String symbol, String interval, long start);
}
