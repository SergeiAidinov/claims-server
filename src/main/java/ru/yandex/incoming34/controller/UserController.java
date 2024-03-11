package ru.yandex.incoming34.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.yandex.incoming34.repo.TicketRepo;
import ru.yandex.incoming34.service.AuthService;
import ru.yandex.incoming34.structures.JwtAuthentication;
import ru.yandex.incoming34.structures.SortingOrder;
import ru.yandex.incoming34.structures.TicketStatus;
import ru.yandex.incoming34.structures.entity.Ticket;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/user")
@AllArgsConstructor
@Api(description = "Предоставляет методы, доступные пользователю с ролью \"USER\"", tags = "Контроллер пользователя")
@PreAuthorize("hasAuthority('USER')")
public class UserController {

    private final AuthService authService;
    private final TicketRepo ticketRepo;
    private final Integer itemsPerPage;

    //@PreAuthorize("hasAuthority('USER')")
    @PostMapping("/newDraft")
    @ApiOperation(value = "Создать черновик заявки")
    public ResponseEntity createDraft(@Parameter(description = "Текст черновика", example = "Новый черновик") String ticketDraftText) {
        System.out.println();
        final JwtAuthentication authInfo = authService.getAuthInfo();
        Ticket ticket = new Ticket(authInfo.getClientId(), ticketDraftText);
        ticketRepo.save(ticket);
        return new ResponseEntity(HttpStatus.OK);
    }

    //@PreAuthorize("hasAuthority('USER')")
    @PutMapping("/newTicket")
    @ApiOperation(value = "Подать заявку")
    public ResponseEntity fileTicket(Long ticketId) {
        System.out.println();
        final JwtAuthentication authInfo = authService.getAuthInfo();
        ticketRepo.fileTicket(ticketId);
        return new ResponseEntity(HttpStatus.OK);
    }

    //@PreAuthorize("hasAuthority('USER')")
    @GetMapping("/allTickets")
    @ApiOperation(value = "Посмотреть поданные заявки")
    public List<Ticket> viewTickets(Integer page, SortingOrder sortingOrder){
        final JwtAuthentication authInfo = authService.getAuthInfo();
        Pageable pageable = PageRequest.of(page, itemsPerPage);
        return switch (sortingOrder) {
            case ASCENDING -> ticketRepo.findAllByClientIdOrderByCreationDateAsc(authInfo.getClientId(), pageable);
            case DESCENDING -> ticketRepo.findAllByClientIdOrderByCreationDateDesc(authInfo.getClientId(), pageable);
        };
    }

    //@PreAuthorize("hasAuthority('USER')")
    @GetMapping("/draft")
    @ApiOperation(value = "Получить черновик заявки")
    public Optional<Ticket> getDraftById(Long ticketId){
        final JwtAuthentication authInfo = authService.getAuthInfo();
        return ticketRepo.findByTicketIdAndTicketStatusAndClientId(ticketId, TicketStatus.DRAFT, authInfo.getClientId());
    }

    //@PreAuthorize("hasAuthority('USER')")
    @PostMapping("/editTicket")
    @ApiOperation(value = "Редактировать черновик заявки")
    public ResponseEntity editDraft(String newText, Long ticketId) {
        final JwtAuthentication authInfo = authService.getAuthInfo();
        ticketRepo.editTicket(newText, ticketId,  authInfo.getClientId());
        return new ResponseEntity(HttpStatus.OK);
    }

}
