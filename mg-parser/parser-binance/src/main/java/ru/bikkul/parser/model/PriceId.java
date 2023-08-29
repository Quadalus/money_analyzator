package ru.bikkul.parser.model;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Embeddable
@Setter
@Getter
@NoArgsConstructor
public class PriceId implements Serializable {
    private Long code;
    private LocalDateTime time;
}
