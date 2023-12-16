package ru.bikkul.service;

public interface ParserInitService {
    void initKlineParser();

    void initOrderBookParser();

    void sendDataToAnalyzer();
}
