package ru.bikkul.parser.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.bikkul.parser.model.Code;
import ru.bikkul.parser.model.Price;
import ru.bikkul.parser.repository.PriceRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PriceServiceImpl {
    private final PriceRepository priceRepository;

    public Optional<Price> getLastPrice(Code code) {
        return priceRepository.findFirstByCodeOrderByTimeDesc(code);
    }

    public void clear(LocalDateTime upTo) {
        List<Price> toDelete = priceRepository.findAllByTimeLessThan(upTo);
        priceRepository.deleteAll(toDelete);
    }

    public Price save(Code code, BigDecimal priceVal, LocalDateTime time) {
        Price price = new Price();
        price.setCode(code);
        price.setValue(priceVal);
        price.setTime(time);
        return priceRepository.save(price);
    }
}
