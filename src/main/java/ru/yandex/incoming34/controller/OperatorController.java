package ru.yandex.incoming34.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.incoming34.repo.TicketRepo;
import ru.yandex.incoming34.service.AuthService;
import ru.yandex.incoming34.structures.SortingOrder;
import ru.yandex.incoming34.structures.dto.AbstractTicketWithUserName;

import java.util.List;

@RestController
@RequestMapping("api/operator")
@AllArgsConstructor
@Api(description = "Предоставляет эндпойнты, доступные пользователю с ролью \"OPERATOR\"", tags = "Контроллер оператора")
@PreAuthorize("hasAuthority('OPERATOR')")
public class OperatorController {

    private final AuthService authService;
    private final TicketRepo ticketRepo;
    private final Integer itemsPerPage;

    @GetMapping("/allTickets")
    @ApiOperation(value = "Позволяет просматривать отправленные заявки конкретного пользователя по его\n" +
            "имени/части имени  возможностью сортировки по дате создания в оба направления.")
    public List<? extends AbstractTicketWithUserName> viewTickets(
            @Parameter(description = "Запрашиваемая страница", required = true) Integer page,
            @Parameter(description = "Порядок сортировки", required = true) SortingOrder sortingOrder,
            @Parameter(description = "Имя либо часть имени запрашиваемого клиента", required = true) String clientLikeName){
        return switch (sortingOrder) {
            case ASCENDING -> ticketRepo.findAllWithSimilarClientNameAscending("%" + clientLikeName + "%", PageRequest.of(page, itemsPerPage));
            case DESCENDING -> ticketRepo.findAllWithSimilarClientNameDescending("%" + clientLikeName + "%", PageRequest.of(page, itemsPerPage));
        };
    }
}
