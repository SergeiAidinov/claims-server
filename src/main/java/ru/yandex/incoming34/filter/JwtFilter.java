package ru.yandex.incoming34.filter;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;
import ru.yandex.incoming34.exception.AuthException;
import ru.yandex.incoming34.repo.ClientRepo;
import ru.yandex.incoming34.service.JwtProvider;
import ru.yandex.incoming34.structures.JwtAuthentication;
import ru.yandex.incoming34.structures.Role;
import ru.yandex.incoming34.structures.entity.Client;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtFilter extends GenericFilterBean {

    private static final String AUTHORIZATION = "Authorization";

    private final JwtProvider jwtProvider;
    private final ClientRepo clientRepo;
    private final ConcurrentHashMap<String, Optional<Client>> loginClientId = new ConcurrentHashMap();
    private final ConcurrentHashMap<String, LocalDateTime> stoppedToken = new ConcurrentHashMap<>();
    private final Integer tokenRetentionInMinutes;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain fc) throws IOException, ServletException {
        HttpServletRequest q = (HttpServletRequest) request;
        String qq = q.getRequestURI();
        final String token = getTokenFromRequest((HttpServletRequest) request);
        if (qq.equals("/api/auth/logout"))
            stoppedToken.put(token, LocalDateTime.now());
        else if (qq.equals("/api/auth/login") && Objects.nonNull(token)) {
            stoppedToken.remove(token);
        }

        if (token != null && jwtProvider.validateAccessToken(token)) {
            final Claims claims = jwtProvider.getAccessClaims(token);
            final JwtAuthentication jwtInfoToken = generate(claims);
            jwtInfoToken.setAuthenticated(!stoppedToken.containsKey(token));
            SecurityContextHolder.getContext().setAuthentication(jwtInfoToken);
        }
        fc.doFilter(request, response);
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        final String bearer = request.getHeader(AUTHORIZATION);
        if (StringUtils.hasText(bearer) && bearer.startsWith("Bearer ")) {
            return bearer.substring(7);
        }
        return null;
    }

    public JwtAuthentication generate(Claims claims) {
        Long clientId;
        loginClientId.computeIfAbsent(claims.getSubject(),
                c -> clientRepo.findByLogin(claims.getSubject()));
        if (Objects.isNull(loginClientId.get(claims.getSubject()))) {
            loginClientId.remove(claims.getSubject());
            throw new AuthException("Ошибка инициализации");
        } else {
            clientId = loginClientId.get(claims.getSubject()).get().getId();
        }

        final JwtAuthentication jwtAuthentication = new JwtAuthentication();
        jwtAuthentication.setRoles(getRoles(claims));
        jwtAuthentication.setFirstName(claims.get("firstName", String.class));
        jwtAuthentication.setUsername(claims.getSubject());
        jwtAuthentication.setClientId(clientId);
        return jwtAuthentication;
    }

    private Set<Role> getRoles(Claims claims) {
        Set<String> ss = Arrays.asList(claims.get("roles", String.class).split(":")).stream().collect(Collectors.toSet());
        return Arrays.asList(Role.values()).stream().filter(role -> ss.contains(role.name())).collect(Collectors.toSet());
    }

    @Scheduled(fixedRateString = "${app.stopped-token-clean-interval-in-milliseconds}")
    private void setStoppedTokenCleaner() {
        final Set<Map.Entry<String, LocalDateTime>> tokensToBeRemoved = stoppedToken.entrySet().stream()
                .filter(entry -> entry.getValue().isBefore(LocalDateTime.now().minusMinutes(tokenRetentionInMinutes)))
                .collect(Collectors.toUnmodifiableSet());
        for (Map.Entry<String, LocalDateTime> entryToBeRemoved : tokensToBeRemoved) {
            stoppedToken.remove(entryToBeRemoved.getKey(), entryToBeRemoved.getValue());
        }
    }

}