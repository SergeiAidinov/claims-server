package ru.yandex.incoming34.structures.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
//@Data
//@NoArgsConstructor
@NamedNativeQuery(name = "FindAllWithSimilarClientNameQuery", query = "SELECT client_name, ticket_id, tc.client_id, ticket_ts, ticket_status FROM tickets\n" +
        "            join tickets_db.table_clients tc on tc.client_id = tickets.client_id\n" +
        "            WHERE client_name LIKE :clientLikeName ORDER BY ticket_ts ASC", resultSetMapping = "FindAllWithSimilarClientNameMapping")
public class TicketWithUserNameAscending extends AbstractTicketWithUserName {

    /*@Id
    @Column(name = "ticket_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ticketId;

    public TicketWithUserNameAscending(String clientName, Long clientId, LocalDateTime ticketTs, String ticketStatus, Long ticketId) {
        super(clientName, clientId, ticketTs, ticketStatus);
        this.ticketId = ticketId;
    }*/

    public TicketWithUserNameAscending(Long ticketId, String clientName, Long clientId, LocalDateTime ticketTs, String ticketStatus) {
        super(ticketId, clientName, clientId, ticketTs, ticketStatus);
    }

    public TicketWithUserNameAscending() {

    }
}