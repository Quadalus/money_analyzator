package ru.bikkul.service;

import ru.bikkul.utils.Markets;

import java.util.Set;

public interface ParserMarketService {
    void deleteTrackingMarket(Markets market);

    Set<Markets> getTrackingMarkets();

    Markets[] getAvailableMarkets();

    Markets addMarketToTracking(Markets market);

    void deleteAllTrackingMarket();

    void deleteTrackingMarkets(Set<Markets> market);

    Set<Markets> addMarketsToTracking(Set<Markets> markets);
}
