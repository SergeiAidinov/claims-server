package ru.yandex.incoming34.structures.dto;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NamedNativeQuery(name = "FindAllWithSimilarClientNameQueryDescending", query = "SELECT client_name, ticket_id, tc.client_id, ticket_ts, ticket_status FROM tickets\n" +
        "            join tickets_db.table_clients tc on tc.client_id = tickets.client_id\n" +
        "            WHERE (client_name LIKE :clientLikeName AND ticket_status = 'FILED') ORDER BY ticket_ts DESC", resultSetMapping = "FindAllWithSimilarClientNameMappingDescending")
@SqlResultSetMapping(name = "FindAllWithSimilarClientNameMappingDescending",
        classes = {
                @ConstructorResult(
                        columns = {
                                @ColumnResult(name = "ticket_id", type = Long.class),
                                @ColumnResult(name = "client_name", type = String.class),
                                @ColumnResult(name = "tc.client_id", type = Long.class),
                                @ColumnResult(name = "ticket_ts", type = LocalDateTime.class),
                                @ColumnResult(name = "ticket_status", type = String.class)
                        },
                        targetClass = TicketWithUserNameDescending.class
                )})
public class TicketWithUserNameDescending extends AbstractTicketWithUserName{

    public TicketWithUserNameDescending(Long ticketId, String clientName, Long clientId, LocalDateTime ticketTs, String ticketStatus) {
        super(ticketId, clientName, clientId, ticketTs, ticketStatus);
    }

    public TicketWithUserNameDescending(){
        super();
    };
}
