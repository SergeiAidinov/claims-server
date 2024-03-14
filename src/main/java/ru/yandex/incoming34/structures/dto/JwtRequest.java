package ru.yandex.incoming34.structures.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class JwtRequest {

    @Schema(example = "winnie")
    private String login;
    @Schema(example = "1234")
    private String password;

}
