package ru.bikkul.parser.client;

public interface BingxClient {
    String getKline(String symbol, String interval, long start, long end);
}
