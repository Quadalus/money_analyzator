package ru.bikkul.parser.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name = "codes")
@NoArgsConstructor
@AllArgsConstructor
public class Code {
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "id")
    private String name;
}
