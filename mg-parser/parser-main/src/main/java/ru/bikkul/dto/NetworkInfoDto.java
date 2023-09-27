package ru.bikkul.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
import ru.bikkul.utils.json.NetworkInfoDeserializer;
import ru.bikkul.utils.json.NetworkInfoSerializer;

@JsonDeserialize(using = NetworkInfoDeserializer.class)
@JsonSerialize(using = NetworkInfoSerializer.class)
@JsonIgnoreProperties(ignoreUnknown = true)
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class NetworkInfoDto {
    private String coin;
    private Boolean depositEnable;
    private String name;
    private String network;
    private Boolean withdrawEnable;
}
