package ru.bikkul.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CoinInfo {
    private String coin;
    private List<NetworkInfo> networkList;

    public CoinInfo() {
    }

    public CoinInfo(String coin, List<NetworkInfo> networkList) {
        this.coin = coin;
        this.networkList = networkList;
    }

    public String getCoin() {
        return coin;
    }

    public void setCoin(String coin) {
        this.coin = coin;
    }

    public List<NetworkInfo> getNetworkList() {
        return networkList;
    }

    public void setNetworkList(List<NetworkInfo> networkList) {
        this.networkList = networkList;
    }
}
