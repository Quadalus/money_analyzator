package ru.bikkul.analyzator.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Table(name = "order_book_spread")
@AllArgsConstructor
@NoArgsConstructor
public class OrderBookSpread {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "pair", nullable = false)
    private String pair;

    @Column(name = "market_base_name", nullable = false)
    private String marketBaseName;

    @Column(name = "market_quote_name", nullable = false)
    private String marketQuoteName;

    @Column(name = "market_base_price", nullable = false)
    private BigDecimal basePrice;

    @Column(name = "market_quote_price", nullable = false)
    private BigDecimal quotePrice;

    @Column(name = "market_base_volume", nullable = false)
    private BigDecimal baseVolume;

    @Column(name = "market_quote_volume", nullable = false)
    private BigDecimal quoteVolume;

    @Column(name = "spread", nullable = false)
    private BigDecimal spread;

    @Column(name = "time", nullable = false)
    private LocalDateTime time = LocalDateTime.now();
}



