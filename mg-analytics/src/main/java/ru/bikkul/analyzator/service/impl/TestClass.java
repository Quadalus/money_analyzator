package ru.bikkul.analyzator.service.impl;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class TestClass {
    public static void main(String[] args) {
        Path path = Path.of(".\\spread.txt");
        Path path1 = Path.of(new File("").getAbsolutePath());
        System.out.println(path1);
        List<String> spreads = List.of("12", "21");

        try {
            Files.write(path, spreads, StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
