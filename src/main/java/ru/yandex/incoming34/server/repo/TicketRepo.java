package ru.yandex.incoming34.server.repo;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ru.yandex.incoming34.server.structures.entity.Ticket;

@Repository
public interface TicketRepo extends PagingAndSortingRepository<Ticket, Long> {
}
