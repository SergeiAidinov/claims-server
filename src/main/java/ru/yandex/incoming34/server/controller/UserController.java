package ru.yandex.incoming34.server.controller;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.yandex.incoming34.server.repo.TicketRepo;
import ru.yandex.incoming34.server.service.AuthService;
import ru.yandex.incoming34.server.structures.JwtAuthentication;
import ru.yandex.incoming34.server.structures.SortingOrder;
import ru.yandex.incoming34.server.structures.dto.TicketDraft;
import ru.yandex.incoming34.server.structures.entity.Ticket;

import java.util.Collections;

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

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/newTicket")
    public ResponseEntity<String> fileTicket(Long ticketId) {
        System.out.println();
        final JwtAuthentication authInfo = authService.getAuthInfo();
        ticketRepo.fileTicket(ticketId);
        return ResponseEntity.ok("Filed ticket " + ticketId);
    }

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/allTickets")
    public Iterable<Ticket> viewTickets(SortingOrder sortingOrder){
        final JwtAuthentication authInfo = authService.getAuthInfo();
        return switch (sortingOrder) {
            case ASCENDING -> ticketRepo.findAllByClientIdOrderByCreationDateAsc(authInfo.getClientId());
            case DESCENDING -> ticketRepo.findAllByClientIdOrderByCreationDateDesc(authInfo.getClientId());
        };
    }

}
