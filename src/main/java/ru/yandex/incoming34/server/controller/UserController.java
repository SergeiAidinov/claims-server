package ru.yandex.incoming34.server.controller;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.yandex.incoming34.server.repo.TicketRepo;
import ru.yandex.incoming34.server.service.AuthService;
import ru.yandex.incoming34.server.structures.JwtAuthentication;
import ru.yandex.incoming34.server.structures.dto.TicketDraft;
import ru.yandex.incoming34.server.structures.entity.Ticket;

@RestController
@RequestMapping("api/user")
@AllArgsConstructor
public class UserController {

    private final AuthService authService;
    private final TicketRepo ticketRepo;

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/newDraft")
    public ResponseEntity<String> createDraft(String ticketDraftText) {
        System.out.println();
        final JwtAuthentication authInfo = authService.getAuthInfo();
        Ticket ticket = new Ticket(authInfo.getClientId(), ticketDraftText);
        ticketRepo.save(ticket);
        return ResponseEntity.ok("Created ticket Draft" + authInfo.getPrincipal() + "!");
    }
}
