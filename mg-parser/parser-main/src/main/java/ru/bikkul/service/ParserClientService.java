package ru.bikkul.service;

import ru.bikkul.dto.CoinInfoDto;

import java.util.List;
import java.util.Map;

public interface ParserClientService {

    Map<String, List<CoinInfoDto>> getAllCoinInfo();
}
