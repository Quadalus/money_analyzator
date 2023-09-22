package ru.bikkul.service.impl;

import org.springframework.stereotype.Service;
import ru.bikkul.service.ParserMarketService;
import ru.bikkul.utils.Markets;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ParserMarketServiceImpl implements ParserMarketService {
    private final Set<Markets> markets = new HashSet<>();

    private ParserMarketServiceImpl() {
        fillMarkets();
    }

    @Override
    public Markets[] getAvailableMarkets() {
        return Markets.values();
    }

    @Override
    public Set<Markets> getTrackingMarkets() {
        return this.markets;
    }

    @Override
    public void deleteTrackingMarket(Markets market) {
        this.markets.remove(market);
    }


    @Override
    public Markets addMarketToTracking(Markets market) {
        this.markets.add(market);
        return market;
    }

    @Override
    public void deleteAllTrackingMarket() {
        this.markets.clear();
    }

    @Override
    public void deleteTrackingMarkets(Set<Markets> market) {
        this.markets.removeAll(market);
    }

    @Override
    public Set<Markets> addMarketsToTracking(Set<Markets> markets) {
        this.markets.addAll(markets);
        return markets;
    }

    private void fillMarkets() {
        List<Markets> list = Arrays.stream(Markets.values()).toList();
        this.markets.addAll(list);
    }
}
