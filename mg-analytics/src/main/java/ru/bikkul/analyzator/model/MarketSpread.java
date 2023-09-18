package ru.bikkul.analyzator.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@Entity
@Table(name = "market_spread")
@AllArgsConstructor
@NoArgsConstructor
public class MarketSpread {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "pair")
    private String pair;

    @Column(name = "market_base_name")
    private String marketBaseName;

    @Column(name = "market_quote_name")
    private String marketQuoteName;

    @Column(name = "market_base_price")
    private BigDecimal basePrice;

    @Column(name = "market_quote_price")
    private BigDecimal quotePrice;

    @Column(name = "market_base_volume")
    private BigDecimal baseVolume;

    @Column(name = "market_quote_volume")
    private BigDecimal quoteVolume;

    @Column(name = "spread")
    private BigDecimal spread;
}



