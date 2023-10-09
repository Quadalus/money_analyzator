package ru.bikkul.service;

import ru.bikkul.dto.coin.CoinInfoDto;

import java.util.List;

public interface ParserClientService {

    List<CoinInfoDto> getAllCoinInfo();
}
