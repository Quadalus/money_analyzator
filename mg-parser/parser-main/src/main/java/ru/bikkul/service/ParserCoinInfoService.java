package ru.bikkul.service;

import ru.bikkul.dto.MarketCoinInfoDto;

import java.util.List;

public interface ParserCoinInfoService {
    List<MarketCoinInfoDto> saveAllCoinInfo();
    List<MarketCoinInfoDto> getAllCoinInfo();
}
