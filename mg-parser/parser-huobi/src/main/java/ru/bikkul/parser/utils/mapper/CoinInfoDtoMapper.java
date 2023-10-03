package ru.bikkul.parser.utils.mapper;

import ru.bikkul.parser.domain.coin.Chains;
import ru.bikkul.parser.domain.coin.CoinInfo;
import ru.bikkul.parser.domain.coin.NetworkInfo;
import ru.bikkul.parser.dto.CoinInfoDto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CoinInfoDtoMapper {
    private CoinInfoDtoMapper() {
    }

    public static List<CoinInfoDto> toCoinInfoDto(CoinInfo coinInfo) {
        List<CoinInfoDto> coinInfoDtos = new ArrayList<>();
        Map<String, List<NetworkInfo>> coinsData = new HashMap<>();

        filCoinsData(coinInfo, coinsData);
        fillCoinsList(coinInfoDtos, coinsData);

        return coinInfoDtos;
    }

    private static void filCoinsData(CoinInfo coinInfo, Map<String, List<NetworkInfo>> coinsData) {
        for (Chains chains : coinInfo.getData()) {
            String coinName = chains.getCurrency().toUpperCase();

            if (!coinsData.containsKey(coinName)) {
                coinsData.put(coinName, new ArrayList<>());
            }
            coinsData.get(coinName).addAll(chains.getChains());
        }
    }

    private static void fillCoinsList(List<CoinInfoDto> coinInfoDtos, Map<String, List<NetworkInfo>> coinsData) {
        for (Map.Entry<String, List<NetworkInfo>> coinData : coinsData.entrySet()) {
            CoinInfoDto coinInfoDto = new CoinInfoDto();
            String key = coinData.getKey();
            List<NetworkInfo> value = coinData.getValue();
            coinInfoDto.setCoin(key);
            coinInfoDto.setNetworkList(value);
            coinInfoDtos.add(coinInfoDto);
        }
    }
}
