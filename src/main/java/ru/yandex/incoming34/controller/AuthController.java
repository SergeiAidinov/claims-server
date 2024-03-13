package ru.yandex.incoming34.controller;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
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
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest authRequest) {
        final JwtResponse token = authService.login(authRequest);
        return ResponseEntity.ok(token);
    }

    @GetMapping("token/{request}")
    public ResponseEntity<JwtResponse> getNewAccessToken(@PathVariable String request) {
        final JwtResponse token = authService.getAccessToken(request);
        return ResponseEntity.ok(token);
    }

    @GetMapping("refresh/{request}")
    public ResponseEntity<JwtResponse> getNewRefreshToken(@PathVariable String request) {
        final JwtResponse token = authService.refresh(request);
        return ResponseEntity.ok(token);
    }

}
