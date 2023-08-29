package ru.bikkul.parser.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.bikkul.parser.model.Code;
import ru.bikkul.parser.model.Price;
import ru.bikkul.parser.model.PriceId;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface PriceRepository extends JpaRepository<Price, PriceId> {
    List<Price> findAllByTimeLessThan(LocalDateTime time);

    Optional<Price> findFirstByCodeOrderByTimeDesc(Code code);
}
