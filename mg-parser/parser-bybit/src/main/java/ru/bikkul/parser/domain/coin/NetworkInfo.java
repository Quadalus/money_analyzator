package ru.bikkul.parser.domain.coin;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ru.bikkul.parser.utils.json.NetworkInfoDeserializer;
import ru.bikkul.parser.utils.json.NetworkInfoSerializer;

@JsonDeserialize(using = NetworkInfoDeserializer.class)
@JsonSerialize(using = NetworkInfoSerializer.class)
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@Setter
@Getter
@ToString
public class NetworkInfo {
    private String marketName;
    private String coin;
    private String name;
    private Boolean depositEnable;
    private Boolean withdrawEnable;
    private String networkFee;
}
