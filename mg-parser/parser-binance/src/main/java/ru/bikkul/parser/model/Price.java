package ru.bikkul.parser.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@IdClass(PriceId.class)
@Setter
@Getter
@Table(name = "prices")
@NoArgsConstructor
@AllArgsConstructor
public class Price {
    @Id
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name= "code_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "code_key"))
    private Code code;

    @Id
    @Column(name = "time")
    private LocalDateTime time;

    @Column(name = "value")
    private BigDecimal value;
}
