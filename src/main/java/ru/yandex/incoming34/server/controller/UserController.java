package ru.yandex.incoming34.server.controller;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.incoming34.server.repo.TicketRepo;
import ru.yandex.incoming34.server.service.AuthService;
import ru.yandex.incoming34.server.structures.JwtAuthentication;

@RestController
@RequestMapping("api/user")
@AllArgsConstructor
public class UserController {

    private final AuthService authService;
    private final TicketRepo ticketRepo;

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/newDraft")
    public ResponseEntity<String> createDraft() {
        System.out.println();
        final JwtAuthentication authInfo = authService.getAuthInfo();
        return ResponseEntity.ok("Hello user " + authInfo.getPrincipal() + "!");
    }
}
