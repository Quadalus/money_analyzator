package ru.bikkul.parser.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Setter
@Getter
@Table(name = "codes", uniqueConstraints = {
        @UniqueConstraint(name = "name_unique", columnNames = "name")
})
@NoArgsConstructor
@AllArgsConstructor
public class Code {
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "code")
    private List<Price> prices;
}
