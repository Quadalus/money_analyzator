package ru.bikkul.analyzator.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.bikkul.analyzator.model.KlineSpread;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface KlineSpreadRepository extends JpaRepository<KlineSpread, Long> {
    //AndStartIsBeforeAndEndIsAfter
    List<KlineSpread> searchKlineSpreadBySpreadIsGreaterThanAndTimeIsAfter(BigDecimal targetSpread, LocalDateTime start);

    void deleteKlineSpreadByTimeBefore(LocalDateTime time);
}
