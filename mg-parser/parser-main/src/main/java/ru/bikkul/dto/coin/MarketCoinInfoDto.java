package ru.bikkul.dto.coin;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class MarketCoinInfoDto {
    private String marketName;
    private String coinName;
    private String networkName;
    private String withdrawFee;
    private Boolean isDepositEnable;
    private Boolean isWithdrawEnable;
}
