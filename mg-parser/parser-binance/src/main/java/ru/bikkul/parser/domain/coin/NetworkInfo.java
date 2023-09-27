package ru.bikkul.parser.domain.coin;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import ru.bikkul.parser.utils.json.NetworkInfoDeserializer;
import ru.bikkul.parser.utils.json.NetworkInfoSerializer;

@JsonDeserialize(using = NetworkInfoDeserializer.class)
@JsonSerialize(using = NetworkInfoSerializer.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class NetworkInfo {
    private String coin;
    private String name;
    private String network;
    private Boolean depositEnable;
    private Boolean withdrawEnable;

    public NetworkInfo() {
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
