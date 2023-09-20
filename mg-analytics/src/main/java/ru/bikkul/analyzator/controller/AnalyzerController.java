package ru.bikkul.analyzator.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.bikkul.analyzator.dto.KlineDataDto;
import ru.bikkul.analyzator.dto.KlineDataRequestDto;
import ru.bikkul.analyzator.service.AnalyzerService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("${analyzer.api.prefix}" + "/analyzer")
@RequiredArgsConstructor
public class AnalyzerController {
    private final AnalyzerService analyzerService;

    @PostMapping("/klines")
    public List<KlineDataDto> saveKlinesData(@RequestBody Map<String, List<KlineDataRequestDto>> klinesData) {
        return analyzerService.saveKlinesData(klinesData);
    }

    @PostMapping("/spread")
    public BigDecimal setSpreadTarget(String spreadTarget) {
        return analyzerService.setSpreadTarget(spreadTarget);
    }

    @GetMapping("/spread")
    public BigDecimal getSpreadTarget() {
        return analyzerService.getSpreadTarget();
    }
}
