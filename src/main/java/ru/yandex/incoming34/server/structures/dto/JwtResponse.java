package ru.yandex.incoming34.server.structures.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class JwtResponse {

    private final String type = "Bearer";
    private final Long userId;
    private final String accessToken;
    private final String refreshToken;

}
