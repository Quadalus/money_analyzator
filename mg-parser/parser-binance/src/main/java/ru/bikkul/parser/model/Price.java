package ru.bikkul.parser.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@Table(name = "prices")
@NoArgsConstructor
@AllArgsConstructor
public class Price {
    @Id
    private Code code;

    @Id
    private LocalDateTime time;
}
