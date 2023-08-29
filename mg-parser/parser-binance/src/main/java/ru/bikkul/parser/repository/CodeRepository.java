package ru.bikkul.parser.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.bikkul.parser.model.Code;

import java.util.Optional;

public interface CodeRepository extends JpaRepository<Code, Long> {
    Optional<Code> getByName(String name);
}
