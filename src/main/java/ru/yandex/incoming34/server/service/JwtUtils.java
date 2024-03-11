package ru.yandex.incoming34.server.service;

import io.jsonwebtoken.Claims;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.yandex.incoming34.server.structures.JwtAuthentication;
import ru.yandex.incoming34.server.structures.Role;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

//@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class JwtUtils {

    public static JwtAuthentication generate(Claims claims) {
        final JwtAuthentication jwtInfoToken = new JwtAuthentication();
        jwtInfoToken.setRoles(getRoles(claims));
        jwtInfoToken.setFirstName(claims.get("firstName", String.class));
        jwtInfoToken.setUsername(claims.getSubject());
        jwtInfoToken.setClientId(1L);
        return jwtInfoToken;
    }

    private static Set<Role> getRoles(Claims claims) {
        List<Role> rr = Arrays.asList(Role.values());
        Set<String> ss = Arrays.asList(claims.get("roles", String.class).split(":")).stream().collect(Collectors.toSet());
        Set<Role> qq = rr.stream().filter(role -> ss.contains(role.name())).collect(Collectors.toSet());
       return qq;
    }

}
