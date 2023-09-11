package ru.bikkul.parser.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
public class CommonParserClient {
    private final WebClient client;

    public CommonParserClient(@Value("${shareit-server.url}") String url) {
        this.client = WebClient.create(url);
    }

    /*public UserDto getUserById(long userId) {
        return client
                .get()
                .uri("/users/{userId}", userId)
                .retrieve()
                .bodyToMono(UserDto.class)
                .block();
    }

    public List<UserDto> getAllUsers(int from, int size) {
        return client.get()
                .uri(uriBuilder -> uriBuilder.path("/users")
                        .queryParam("from", from)
                        .queryParam("size", size)
                        .build())
                .retrieve()
                .bodyToFlux(UserDto.class)
                .collectList()
                .block();
    }


    public UserDto saveUser(UserDtoFromRequest userDto) {
        return client.post()
                .uri("/users")
                .bodyValue(userDto)
                .retrieve()
                .bodyToMono(UserDto.class)
                .block();
    }*/
}
