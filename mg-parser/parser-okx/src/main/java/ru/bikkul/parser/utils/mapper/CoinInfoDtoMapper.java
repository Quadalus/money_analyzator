package ru.bikkul.parser.utils.mapper;

import ru.bikkul.parser.domain.coin.CoinInfo;
import ru.bikkul.parser.dto.CoinInfoDto;

public class CoinInfoDtoMapper {
    private CoinInfoDtoMapper() {
    }

    public static CoinInfoDto toCoinInfoDto(CoinInfo coinInfo) {
        CoinInfoDto coinInfoDto = new CoinInfoDto();

        String coinName = coinInfo.getData().get(0).getCoin();
        coinInfoDto.setCoin(coinName);
        coinInfoDto.setNetworkList(coinInfo.getData());
        return coinInfoDto;
    }
}
