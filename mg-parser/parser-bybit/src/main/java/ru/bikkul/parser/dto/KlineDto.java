package ru.bikkul.parser.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class KlineDto {
    @NotEmpty
    private String close;
    @NotEmpty
    private String volume;
}
