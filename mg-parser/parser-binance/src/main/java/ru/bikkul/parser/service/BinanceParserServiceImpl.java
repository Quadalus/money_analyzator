package ru.bikkul.parser.service;

import com.binance.api.client.domain.market.Candlestick;
import com.binance.api.client.domain.market.OrderBook;
import com.binance.api.client.domain.market.OrderBookEntry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.bikkul.parser.client.BinanceParserClient;
import ru.bikkul.parser.dto.AskDto;
import ru.bikkul.parser.dto.BidDto;
import ru.bikkul.parser.dto.KlineDto;
import ru.bikkul.parser.dto.KlineFullDataDto;
import ru.bikkul.parser.utils.AskDtoMapper;
import ru.bikkul.parser.utils.BidsDtoMapper;
import ru.bikkul.parser.utils.KlineDtoMapper;
import ru.bikkul.parser.utils.KlineFullDataDtoMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BinanceParserServiceImpl implements BinanceParserService {
    private final BinanceParserClient binanceParseClient;

    @Override
    public Map<String, KlineFullDataDto> getKlineForFiveMin(Set<String> pairs) {
        Map<String, KlineFullDataDto> klines = new HashMap<>();

        for (String pair: pairs) {
            List<KlineDto> klineForFiveMin = getKline(binanceParseClient.getKlineForFiveMin(pair));
            klines.put(pair, KlineFullDataDtoMapper.toKlineFullDataDto(klineForFiveMin));
        }
        return klines;
    }

    @Override
    public void getSpotData(OrderBook orderBook, String pair) {
        Map<String, Object> book = new HashMap<>();
        List<BidDto> bids = parseBids(orderBook.getBids(), pair);
        List<AskDto> asks = parseAsks(orderBook.getAsks(), pair);
    }

    private List<BidDto> parseBids(List<OrderBookEntry> bids, String pair) {
        return bids.stream()
                .map(bid -> BidsDtoMapper.toBidDto(bid, pair))
                .collect(Collectors.toList());
    }

    private List<AskDto> parseAsks(List<OrderBookEntry> asks, String pair) {
        return asks.stream()
                .map(ask -> AskDtoMapper.toAskDto(ask, pair))
                .collect(Collectors.toList());
    }

    private List<KlineDto> getKline(List<Candlestick> candlesticks) {
        return candlesticks.stream()
                .map(KlineDtoMapper::toKlineDto)
                .collect(Collectors.toList());
    }
}
