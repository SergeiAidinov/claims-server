package ru.yandex.incoming34.repo;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.incoming34.structures.TicketStatus;
import ru.yandex.incoming34.structures.dto.AbstractTicketWithUserName;
import ru.yandex.incoming34.structures.entity.Ticket;

import java.util.List;
import java.util.Optional;

@Repository
public interface TicketRepo extends PagingAndSortingRepository<Ticket, Long> {

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "UPDATE tickets SET ticket_status = 'FILED' WHERE ticket_id = :ticketId")
    void fileTicket(@Param(value = "ticketId") Long ticketId);

    List<Ticket> findAllByClientIdOrderByCreationDateAsc(Long clientId, Pageable pageable);

    List<Ticket> findAllByClientIdOrderByCreationDateDesc(Long clientId, Pageable pageable);

    Optional<Ticket> findByTicketIdAndTicketStatusAndClientId(Long ticketId, TicketStatus status, Long clientId);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "UPDATE tickets SET ticket_text = :newText WHERE (ticket_id = :ticketId AND ticket_status = 'DRAFT' AND client_id = :clientId)")
    void editTicket(@Param(value = "newText") String newText, @Param(value = "ticketId") Long ticketId, @Param(value = "clientId") Long clientId);

    @Query(nativeQuery = true, name = "FindAllWithSimilarClientNameQuery")
    List<? extends AbstractTicketWithUserName> findAllWithSimilarClientName(@Param("clientLikeName") String clientLikeName, Pageable pageable);
}
