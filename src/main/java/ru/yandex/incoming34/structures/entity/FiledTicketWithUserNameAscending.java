package ru.yandex.incoming34.structures.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NamedNativeQuery(name = "FindAllWithSimilarClientNameQueryAscending", query = "SELECT client_name, ticket_id, tc.client_id, ticket_ts, ticket_status FROM tickets\n" +
        "            join tickets_db.table_clients tc on tc.client_id = tickets.client_id\n" +
        "            WHERE (client_name LIKE :clientLikeName AND ticket_status = 'FILED') ORDER BY ticket_ts ASC", resultSetMapping = "FindAllWithSimilarClientNameMappingAscending")
@SqlResultSetMapping(name = "FindAllWithSimilarClientNameMappingAscending",
        classes = {
                @ConstructorResult(
                        columns = {
                                @ColumnResult(name = "ticket_id", type = Long.class),
                                @ColumnResult(name = "client_name", type = String.class),
                                @ColumnResult(name = "tc.client_id", type = Long.class),
                                @ColumnResult(name = "ticket_ts", type = LocalDateTime.class),
                                @ColumnResult(name = "ticket_status", type = String.class)
                        },
                        targetClass = FiledTicketWithUserNameAscending.class
                )})
public class FiledTicketWithUserNameAscending extends AbstractTicketWithUserName {

    public FiledTicketWithUserNameAscending(Long ticketId, String clientName, Long clientId, LocalDateTime ticketTs, String ticketStatus) {
        super(ticketId, clientName, clientId, ticketTs, ticketStatus);
    }

    public FiledTicketWithUserNameAscending() {
        super();
    }
}