package ru.bikkul.analyzator.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.bikkul.analyzator.dto.KlineDataDto;
import ru.bikkul.analyzator.dto.KlineDataRequestDto;
import ru.bikkul.analyzator.dto.KlineDataResponseDto;
import ru.bikkul.analyzator.mapper.KlineDataDtoMapper;
import ru.bikkul.analyzator.model.KlineSpread;
import ru.bikkul.analyzator.repository.KlineSpreadRepository;
import ru.bikkul.analyzator.service.AnalyzerService;

import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@EnableScheduling
@RequiredArgsConstructor
public class AnalyzerServiceFileImpl implements AnalyzerService {
    private final KlineSpreadRepository klineSpreadRepository;
    private BigDecimal spreadTarget = new BigDecimal("0.1");

    @Override
    public List<KlineDataDto> saveKlinesData(Map<String, List<KlineDataRequestDto>> klinesData) {
        List<KlineDataDto> klinesDataDto = KlineDataDtoMapper.toKlinesDataDto(klinesData);
        List<KlineSpread> klineSpreads = KlineDataDtoMapper.toKlineSpread(klinesDataDto);
        List<KlineSpread> onlyPlusSpread = plusSpread(klineSpreads);
        List<KlineSpread> savedKlineSpreads = klineSpreadRepository.saveAll(onlyPlusSpread);
        LocalDateTime start = LocalDateTime.now().minusMinutes(1);
        printKlineSpeads(KlineDataDtoMapper.toKlinesDataResponseDto(klineSpreadRepository.searchKlineSpreadBySpreadIsGreaterThanAndTimeIsAfter(spreadTarget, start)));
        return KlineDataDtoMapper.toKlinesDataDto(savedKlineSpreads);
    }

    @Override
    public BigDecimal setSpreadTarget(String spreadTarget) {
        this.spreadTarget = new BigDecimal(spreadTarget);
        return this.spreadTarget;
    }

    @Override
    public BigDecimal getSpreadTarget() {
        return this.spreadTarget;
    }

    @Scheduled(fixedRate = 3600000)
    private void clearDB() {
        LocalDateTime oneHourBeforeNow = LocalDateTime.now().minusMinutes(3600);
        klineSpreadRepository.deleteKlineSpreadByTimeBefore(oneHourBeforeNow);
    }

    @Scheduled(fixedRate = 900000)
    private void clearFile() {
        Path path = Path.of(".\\speads.txt");
        try {
            FileWriter fw = new FileWriter(path.toFile(), false);
            fw.write("==========================================");
            fw.close();
            log.info("file clear");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private List<KlineSpread> plusSpread(List<KlineSpread> klines) {
        return klines.stream()
                .filter(kline -> kline.getSpread().compareTo(new BigDecimal("0")) > 0)
                .collect(Collectors.toList());
    }

    private void printKlineSpeads(List<KlineDataResponseDto> savedKlineSpreads) {
        Path path = Path.of(".\\speads.txt");
        List<String> spreads = klinesToString(savedKlineSpreads);

        try {
            Files.write(path, spreads, StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private List<String> klinesToString(List<KlineDataResponseDto> savedKlineSpreads) {
        List<String> klinesSpeads = new ArrayList<>();

        for (KlineDataResponseDto kline : savedKlineSpreads) {
            String text = String
                    .format("%s | %s (buy) price=%s volume=%s(%s) --> %s (sell) price=%s volume=%s(%s) | spread=%s | time=%s",
                            kline.getPair(), kline.getMarketBaseName(), kline.getBasePrice(), kline.getBaseVolume(), kline.getBaseVolume25Percent(),
                            kline.getMarketQuoteName(), kline.getQuotePrice(), kline.getQuoteVolume(), kline.getQuoteVolume25Percent(),
                            kline.getSpread(), kline.getTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            klinesSpeads.add(text);
        }
        return klinesSpeads;
    }
}
