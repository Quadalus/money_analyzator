package ru.bikkul.service;

import ru.bikkul.dto.CoinInfoDto;

import java.util.List;

public interface ParserClientService {

    List<CoinInfoDto> getAllCoinInfo();
}
