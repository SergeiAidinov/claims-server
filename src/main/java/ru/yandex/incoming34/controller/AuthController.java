package ru.yandex.incoming34.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.incoming34.service.AuthService;
import ru.yandex.incoming34.structures.dto.JwtRequest;
import ru.yandex.incoming34.structures.dto.JwtResponse;

@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
@Api(description = "Предоставляет эндпойнты для входа в систему", tags = "Контроллер аутентификации")
public class AuthController {

    private final AuthService authService;

    @PostMapping("login")
    @ApiOperation(value = "Позволяет получить accessToken и refreshToken по логину и паролю")
    public ResponseEntity<JwtResponse> login(
            @Parameter(description = "Логин и п пароль", required = true) @RequestBody JwtRequest authRequest
    ) {
        final JwtResponse token = authService.login(authRequest);
        return ResponseEntity.ok(token);
    }

    @GetMapping("new_access_token/{request}")
    @ApiOperation(value = "Позволяет получить accessToken по refreshToken")
    public ResponseEntity<JwtResponse> getNewAccessToken(@PathVariable String request) {
        final JwtResponse token = authService.getAccessToken(request);
        return ResponseEntity.ok(token);
    }

    @GetMapping("new_tokens{request}")
    @ApiOperation(value = "Позволяет получить новую пару  accessToken и refreshToken по имеющемуся refreshToken")
    public ResponseEntity<JwtResponse> getNewRefreshToken(@PathVariable String request) {
        final JwtResponse token = authService.refresh(request);
        return ResponseEntity.ok(token);
    }

    @GetMapping("logout")
    @ApiOperation(value = "Позволяет выйти из системы. Для повторного входа нужно получть новый accessToken")
    public ResponseEntity logout() {
        //Разлогинивание осуществляется в методе doFilter класса JwtFilter
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
