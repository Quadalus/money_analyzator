package ru.bikkul.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.bikkul.dto.CoinInfoDto;
import ru.bikkul.dto.MarketCoinInfoDto;
import ru.bikkul.model.MarketCoinInfo;
import ru.bikkul.repository.MarketCoinInfoRepository;
import ru.bikkul.service.ParserClientService;
import ru.bikkul.service.ParserCoinInfoService;
import ru.bikkul.utils.mapper.MarketCoinDtoMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ParserCoinInfoServiceImpl implements ParserCoinInfoService {
    private final MarketCoinInfoRepository coinInfoRepository;
    private final ParserClientService parserClientService;

    @Override
    public List<MarketCoinInfoDto> saveAllCoinInfo() {
        coinInfoRepository.deleteAll();
        List<CoinInfoDto> allCoinInfo = parserClientService.getAllCoinInfo();
        List<MarketCoinInfo> coins = MarketCoinDtoMapper.toMarketCoins(allCoinInfo);
        List<MarketCoinInfo> savedCoins = coinInfoRepository.saveAll(coins);
        return MarketCoinDtoMapper.toMarketCoinsDto(savedCoins);
    }

    @Override
    public List<MarketCoinInfoDto> getAllCoinInfo() {
        List<MarketCoinInfo> coinInfos = coinInfoRepository.findAll();
        return MarketCoinDtoMapper.toMarketCoinsDto(coinInfos);
    }
}
