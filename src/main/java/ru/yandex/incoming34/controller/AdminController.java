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
import ru.yandex.incoming34.repo.UserRepo;
import ru.yandex.incoming34.structures.SortingOrder;
import ru.yandex.incoming34.structures.dto.AbstractTicketWithUserName;
import ru.yandex.incoming34.structures.entity.User;

import java.util.List;

@RestController
@RequestMapping("api/admin")
@AllArgsConstructor
@Api(description = "Предоставляет эндпойнты, доступные пользователю с ролью \"ADMIN\"", tags = "Контроллер администратора")
//@PreAuthorize("hasAuthority('ADMIN')")
public class AdminController {

    private final UserRepo userRepo;
    private final TicketRepo ticketRepo;
    private final Integer itemsPerPage;

    @GetMapping("/allUsers")
    @ApiOperation(value = "Посмотреть список пользователей")
    public Iterable<User> getAllUsers() {
        return userRepo.findAll();
    }

    @GetMapping("/allTickets")
    @ApiOperation(value = "Позволяет просматривать отправленные заявки конкретного пользователя по его\n" +
            "имени/части имени  возможностью сортировки по дате создания в оба направления.")
    public List<? extends AbstractTicketWithUserName> viewTickets(
            @Parameter(description = "Запрашиваемая страница", required = true) Integer page,
            @Parameter(description = "Имя либо часть имени запрашиваемого клиента", required = true) String clientLikeName){
       return ticketRepo.findAllUnFiledTicketWithUserName("%" + clientLikeName + "%", PageRequest.of(page, itemsPerPage));
    };
}
