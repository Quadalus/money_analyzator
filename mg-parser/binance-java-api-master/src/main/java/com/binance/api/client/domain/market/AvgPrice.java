package com.binance.api.client.domain.market;

import com.binance.api.client.constant.BinanceApiConstants;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class AvgPrice {
    private long mins;

    private String price;

    public long getMins() {
        return mins;
    }

    public void setMins(long mins) {
        this.mins = mins;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return  new ToStringBuilder(this, BinanceApiConstants.TO_STRING_BUILDER_STYLE)
                .append("mins", mins)
                .append("price", price)
                .toString();
    }
}
