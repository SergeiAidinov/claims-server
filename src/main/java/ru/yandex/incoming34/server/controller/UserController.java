package ru.yandex.incoming34.server.controller;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.yandex.incoming34.server.repo.TicketRepo;
import ru.yandex.incoming34.server.service.AuthService;
import ru.yandex.incoming34.server.structures.JwtAuthentication;
import ru.yandex.incoming34.server.structures.SortingOrder;
import ru.yandex.incoming34.server.structures.TicketStatus;
import ru.yandex.incoming34.server.structures.entity.Ticket;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/user")
@AllArgsConstructor
public class UserController {

    private final AuthService authService;
    private final TicketRepo ticketRepo;
    private final Integer itemsPerPage;

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
    @PutMapping("/newTicket")
    public ResponseEntity<String> fileTicket(Long ticketId) {
        System.out.println();
        final JwtAuthentication authInfo = authService.getAuthInfo();
        ticketRepo.fileTicket(ticketId);
        return ResponseEntity.ok("Filed ticket " + ticketId);
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/allTickets")
    public List<Ticket> viewTickets(Integer page, SortingOrder sortingOrder){
        final JwtAuthentication authInfo = authService.getAuthInfo();
        Pageable pageable = PageRequest.of(page, itemsPerPage);
        return switch (sortingOrder) {
            case ASCENDING -> ticketRepo.findAllByClientIdOrderByCreationDateAsc(authInfo.getClientId(), pageable);
            case DESCENDING -> ticketRepo.findAllByClientIdOrderByCreationDateDesc(authInfo.getClientId(), pageable);
        };
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/draft")
    public Optional<Ticket> getDraftById(Long ticketId){
        final JwtAuthentication authInfo = authService.getAuthInfo();
        return ticketRepo.findByTicketIdAndTicketStatusAndClientId(ticketId, TicketStatus.DRAFT, authInfo.getClientId());
    }

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/editTicket")
    public ResponseEntity<String> editDraft(String newText, Long ticketId) {
        final JwtAuthentication authInfo = authService.getAuthInfo();
        ticketRepo.editTicket(newText, ticketId,  authInfo.getClientId());
        /*Optional<Ticket> ticketOptional = ticketRepo.findById(ticketId);*/
        return ResponseEntity.ok("Works");
    }

}
