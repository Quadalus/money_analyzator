package ru.bikkul.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.bikkul.model.MarketCoinInfo;

public interface MarketCoinInfoRepository extends JpaRepository<MarketCoinInfo, Integer> {
}
