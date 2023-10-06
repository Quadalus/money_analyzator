package ru.bikkul.parser.dto;

import lombok.*;
import ru.bikkul.parser.domain.coin.NetworkInfo;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CoinInfoDto {
    private String coin;
    private List<NetworkInfo> networkList;
}
