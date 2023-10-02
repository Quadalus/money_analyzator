package ru.bikkul.parser.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.bikkul.parser.domain.coin.NetworkInfo;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CoinInfoDto {
    private String coin;
    private List<NetworkInfo> networkList;
}
