package ru.yandex.incoming34.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.incoming34.repo.TicketRepo;
import ru.yandex.incoming34.service.AuthService;
import ru.yandex.incoming34.structures.JwtAuthentication;
import ru.yandex.incoming34.structures.SortingOrder;
import ru.yandex.incoming34.structures.dto.TicketWithUserName;

@RestController
@RequestMapping("api/operator")
@AllArgsConstructor
@Api(description = "Предоставляет методы, доступные пользователю с ролью \"OPERATOR\"", tags = "Контроллер оператора")
@PreAuthorize("hasAuthority('OPERATOR')")
public class OperatorController {

    private final AuthService authService;
    private final TicketRepo ticketRepo;
    private final Integer itemsPerPage;

    @GetMapping("/allTickets")
    @ApiOperation(value = "Просматривать отправленные заявки только конкретного пользователя по его имени/части имени")
    public Iterable<TicketWithUserName> viewTickets(Integer page, SortingOrder sortingOrder, Long clientId){
        final JwtAuthentication authInfo = authService.getAuthInfo();
        Iterable<TicketWithUserName> s = ticketRepo.findAllByClientName("Winnie-the-Pooh");
        /*return switch (sortingOrder) {
            case ASCENDING -> ticketRepo.findAllByClientIdOrderByCreationDateAsc(clientId, PageRequest.of(page, itemsPerPage));
            case DESCENDING -> ticketRepo.findAllByClientIdOrderByCreationDateDesc(clientId, PageRequest.of(page, itemsPerPage));
        };*/
       return s;
    }
}
