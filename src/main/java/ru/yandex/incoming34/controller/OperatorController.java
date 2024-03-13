package ru.yandex.incoming34.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.incoming34.repo.ClientRepo;
import ru.yandex.incoming34.repo.TicketRepo;
import ru.yandex.incoming34.service.AuthService;
import ru.yandex.incoming34.structures.Role;
import ru.yandex.incoming34.structures.SortingOrder;
import ru.yandex.incoming34.structures.entity.AbstractTicketWithUserName;
import ru.yandex.incoming34.structures.entity.Ticket;

import java.util.*;

@RestController
@RequestMapping("api/operator")
@AllArgsConstructor
@Api(description = "Предоставляет эндпойнты, доступные пользователю с ролью \"OPERATOR\"", tags = "Контроллер оператора")
//@PreAuthorize("hasAuthority('OPERATOR')")
public class OperatorController {

    private final AuthService authService;
    private final TicketRepo ticketRepo;
    private final Integer itemsPerPage;
    private final ClientRepo clientRepo;

    @GetMapping("/allTickets")
    @ApiOperation(value = "Позволяет просматривать отправленные заявки конкретного пользователя по его\n" +
            "имени/части имени  возможностью сортировки по дате создания в оба направления.")
    public List<? extends AbstractTicketWithUserName> viewTickets(
            @Parameter(description = "Запрашиваемая страница", required = true) Integer page,
            @Parameter(description = "Порядок сортировки", required = true) SortingOrder sortingOrder,
            @Parameter(description = "Имя либо часть имени запрашиваемого клиента", required = true) String clientLikeName) {
        return switch (sortingOrder) {
            case ASCENDING ->
                    ticketRepo.findAllWithSimilarClientNameAscending("%" + clientLikeName + "%", PageRequest.of(page, itemsPerPage));
            case DESCENDING ->
                    ticketRepo.findAllWithSimilarClientNameDescending("%" + clientLikeName + "%", PageRequest.of(page, itemsPerPage));
        };
    }

    @GetMapping("/ticket/{ticketId}")
    @ApiOperation(value = "Смотреть заявку по id")
    public Optional<Ticket> viewTicketById(@Parameter(description = "Идентификатор заявки", required = true) @PathVariable Long ticketId) {
        return ticketRepo.findById(ticketId);
    }

    @PutMapping("/acceptTicket/{ticketId}")
    @ApiOperation(value = "Принять заявку по id")
    public ResponseEntity acceptTicketById(@Parameter(description = "Идентификатор заявки", required = true) @PathVariable Long ticketId) {
        ticketRepo.acceptTicketById(ticketId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/declineTicket/{ticketId}")
    @ApiOperation(value = "Отклонить заявку по id")
    public ResponseEntity declineTicketById(@Parameter(description = "Идентификатор заявки", required = true) @PathVariable Long ticketId) {
        ticketRepo.declineTicketById(ticketId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/defineOperator/{clientId}")
    @ApiOperation(value = "Назначить пользователю права оператора")
    public ResponseEntity defineOperator(@Parameter(description = "Идентификатор пользователя", required = true) @PathVariable Long clientId) {
        clientRepo.findById(clientId).ifPresent(client -> {
            final StringJoiner stringJoiner = new StringJoiner(":");
            final Set<String> roles = (new HashSet<>(Arrays.asList(client.getRoles().split(":"))));
            roles.add(Role.OPERATOR.name());
            roles.stream().forEach(s -> stringJoiner.add(s));
            client.setRoles(stringJoiner.toString());
            clientRepo.save(client);
        });
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
