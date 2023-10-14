package ru.bikkul.analyzator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.bikkul.analyzator.model.OrderBookSpread;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface OrderBookSpreadRepository extends JpaRepository<OrderBookSpread, Long> {
    List<OrderBookSpread> searchOrderBookSpreadBySpreadIsGreaterThanAndTimeIsAfter(BigDecimal targetSpread, LocalDateTime start);
}
