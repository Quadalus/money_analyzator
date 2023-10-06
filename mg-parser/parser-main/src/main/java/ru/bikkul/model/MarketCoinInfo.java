package ru.bikkul.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "market_coin_info")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class MarketCoinInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "market_name", nullable = false)
    private String marketName;

    @Column(name = "coin_name", nullable = false)
    private String coinName;

    @Column(name = "market_network_name", nullable = false)
    private String networkName;

    @Column(name = "market_withdraw_fee", nullable = false)
    private String withdrawFee;

    @Column(name = "market_network_is_deposit", nullable = false)
    private Boolean isDepositEnable;

    @Column(name = "market_network_is_withdraw", nullable = false)
    private Boolean isWithdrawEnable;
}
