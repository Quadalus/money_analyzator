package ru.bikkul.dto;

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
    private Boolean isDepositEnable;
    private Boolean isWithdrawEnable;
}
