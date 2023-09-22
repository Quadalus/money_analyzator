package ru.bikkul.parser.client;

public interface AscendexClient {
    String getKline(String symbol, String interval, long start, long end, int limit);
}
