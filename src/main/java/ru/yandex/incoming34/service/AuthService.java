package ru.yandex.incoming34.service;

import io.jsonwebtoken.Claims;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import ru.yandex.incoming34.repo.ClientRepo;
import ru.yandex.incoming34.structures.JwtAuthentication;
import ru.yandex.incoming34.structures.Role;
import ru.yandex.incoming34.structures.dto.JwtRequest;
import ru.yandex.incoming34.structures.dto.JwtResponse;
import ru.yandex.incoming34.structures.entity.Client;
import ru.yandex.incoming34.exception.AuthException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final Map<String, String> refreshStorage = new HashMap<>();
    private final JwtProvider jwtProvider;
    private final ClientRepo clientRepo;

    public JwtResponse login(@NonNull JwtRequest authRequest) {
        final Client client = clientRepo.findByLogin(authRequest.getLogin())
                .orElseThrow(() -> new AuthException("Пользователь не найден"));
        if (client.getPassword().equals(authRequest.getPassword())) {
            final String accessToken = jwtProvider.generateAccessToken(client);
            final String refreshToken = jwtProvider.generateRefreshToken(client);
            refreshStorage.put(client.getLogin(), refreshToken);
            return new JwtResponse(client.getId(), accessToken, refreshToken);
        } else {
            throw new AuthException("Неправильный пароль");
        }
    }

    public JwtResponse getAccessToken(@NonNull String refreshToken) {
        if (jwtProvider.validateRefreshToken(refreshToken)) {
            final Claims claims = jwtProvider.getRefreshClaims(refreshToken);
            final String login = claims.getSubject();
            final String saveRefreshToken = refreshStorage.get(login);
            if (saveRefreshToken != null && saveRefreshToken.equals(refreshToken)) {
                final Client client = clientRepo.findByLogin(login)
                        .orElseThrow(() -> new AuthException("Пользователь не найден"));
                final String accessToken = jwtProvider.generateAccessToken(client);
                return new JwtResponse(12341234L, accessToken, null);
            }
        }
        return new JwtResponse(0L, null, null);
    }

    public JwtResponse refresh(@NonNull String refreshToken) {
        if (jwtProvider.validateRefreshToken(refreshToken)) {
            final Claims claims = jwtProvider.getRefreshClaims(refreshToken);
            final String login = claims.getSubject();
            final String saveRefreshToken = refreshStorage.get(login);
            if (saveRefreshToken != null && saveRefreshToken.equals(refreshToken)) {
                final Client client = clientRepo.findByLogin(login)
                        .orElseThrow(() -> new AuthException("Пользователь не найден"));
                final String accessToken = jwtProvider.generateAccessToken(client);
                final String newRefreshToken = jwtProvider.generateRefreshToken(client);
                refreshStorage.put(client.getLogin(), newRefreshToken);
                return new JwtResponse(4674890345L, accessToken, newRefreshToken);
            }
        }
        throw new AuthException("Невалидный JWT токен");
    }

    public JwtAuthentication getAuthInfo() {
        return (JwtAuthentication) SecurityContextHolder.getContext().getAuthentication();
    }

    public void logout(String log) {
        refreshStorage.remove(log);
    }
}
