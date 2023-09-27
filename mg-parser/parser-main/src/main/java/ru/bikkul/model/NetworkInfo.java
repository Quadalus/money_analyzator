package ru.bikkul.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonDeserialize(using = NetworkInfoDeserializer.class)
@JsonSerialize(using = NetworkInfoSerializer.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class NetworkInfo {
    private String coin;
    private Boolean depositEnable;
    private String name;
    private String network;
    private Boolean withdrawEnable;

    public NetworkInfo() {
    }

    public NetworkInfo(String coin, Boolean depositEnable, String name, String network, Boolean withdrawEnable) {
        this.coin = coin;
        this.depositEnable = depositEnable;
        this.name = name;
        this.network = network;
        this.withdrawEnable = withdrawEnable;
    }

    public String getCoin() {
        return coin;
    }

    public void setCoin(String coin) {
        this.coin = coin;
    }

    public Boolean getDepositEnable() {
        return depositEnable;
    }

    public void setDepositEnable(Boolean depositEnable) {
        this.depositEnable = depositEnable;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNetwork() {
        return network;
    }

    public void setNetwork(String network) {
        this.network = network;
    }

    public Boolean getWithdrawEnable() {
        return withdrawEnable;
    }

    public void setWithdrawEnable(Boolean withdrawEnable) {
        this.withdrawEnable = withdrawEnable;
    }
}
