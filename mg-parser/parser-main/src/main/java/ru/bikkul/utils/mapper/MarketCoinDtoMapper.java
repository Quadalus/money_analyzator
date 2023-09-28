package ru.bikkul.utils.mapper;

import ru.bikkul.dto.CoinInfoDto;
import ru.bikkul.dto.MarketCoinInfoDto;
import ru.bikkul.dto.NetworkInfoDto;
import ru.bikkul.model.MarketCoinInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MarketCoinDtoMapper {
    private MarketCoinDtoMapper() {
    }

    public static List<MarketCoinInfo> toMarketCoins(Map<String, List<CoinInfoDto>> coinsInfo) {
        List<MarketCoinInfo> coins = new ArrayList<>();

        for (Map.Entry<String, List<CoinInfoDto>> entry : coinsInfo.entrySet()) {
            String marketName = entry.getKey();
            List<CoinInfoDto> marketCoins = entry.getValue();

            for (CoinInfoDto coin : marketCoins) {
                for (NetworkInfoDto networkInfoDto : coin.getNetworkList()) {
                    MarketCoinInfo marketCoinInfo = new MarketCoinInfo();
                    marketCoinInfo.setMarketName(marketName);
                    marketCoinInfo.setCoinName(coin.getCoin());
                    marketCoinInfo.setNetworkName(networkInfoDto.getName());
                    marketCoinInfo.setIsDepositEnable(networkInfoDto.getDepositEnable());
                    marketCoinInfo.setIsWithdrawEnable(networkInfoDto.getWithdrawEnable());
                    coins.add(marketCoinInfo);
                }
            }
        }
        return coins;
    }

    public static List<MarketCoinInfoDto> toMarketCoinsDto(List<MarketCoinInfo> savedCoins) {
        return savedCoins
                .stream()
                .map(MarketCoinDtoMapper::toMarketCoinDto)
                .toList();
    }

    private static MarketCoinInfoDto toMarketCoinDto(MarketCoinInfo coinInfo) {
        MarketCoinInfoDto marketCoinDto = new MarketCoinInfoDto();

        marketCoinDto.setMarketName(coinInfo.getMarketName());
        marketCoinDto.setCoinName(coinInfo.getCoinName());
        marketCoinDto.setNetworkName(coinInfo.getNetworkName());
        marketCoinDto.setIsDepositEnable(coinInfo.getIsDepositEnable());
        marketCoinDto.setIsWithdrawEnable(coinInfo.getIsWithdrawEnable());

        return marketCoinDto;
    }
}
