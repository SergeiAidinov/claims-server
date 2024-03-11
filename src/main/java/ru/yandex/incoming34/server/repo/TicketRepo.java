package ru.yandex.incoming34.server.repo;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.incoming34.server.structures.entity.Ticket;

import java.util.List;

@Repository
public interface TicketRepo extends PagingAndSortingRepository<Ticket, Long> {

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "UPDATE tickets SET ticket_status = 'FILED' WHERE ticket_id = :ticketId")
    void fileTicket(@Param(value = "ticketId") Long ticketId);

    List<Ticket> findAllByClientIdOrderByCreationDateAsc(Long clientId, Pageable pageable);
    List<Ticket> findAllByClientIdOrderByCreationDateDesc(Long clientId, Pageable pageable);
}
