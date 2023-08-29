package ru.bikkul.parser.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.bikkul.parser.model.Code;
import ru.bikkul.parser.repository.CodeRepository;

@Service
@AllArgsConstructor
public class CodeServiceImpl {
    private final CodeRepository codeRepository;

    public Code save(String codeName) {
        Code code = new Code();
        code.setName(codeName);
        return codeRepository.save(code);
    }

    public Code get(String codeName) {
        return codeRepository.getByName(codeName)
                .orElse(save(codeName));
    }
}
