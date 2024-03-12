package ru.yandex.incoming34.structures.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.yandex.incoming34.structures.TicketStatus;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@NamedNativeQuery(name = "FindAllWithSimilarClientNameQuery", query = "SELECT client_name, ticket_id, tc.client_id, ticket_ts, ticket_status FROM tickets\n" +
        "            join tickets_db.table_clients tc on tc.client_id = tickets.client_id\n" +
        "            WHERE client_name LIKE :clientLikeName", resultSetMapping = "FindAllWithSimilarClientNameMapping")
@SqlResultSetMapping(name = "FindAllWithSimilarClientNameMapping",
        classes = {
                @ConstructorResult(
                        columns = {
                                @ColumnResult(name = "ticket_id", type = Long.class),
                                @ColumnResult(name = "client_name", type = String.class),
                                @ColumnResult(name = "tc.client_id", type = Long.class),
                                @ColumnResult(name = "ticket_ts", type = LocalDateTime.class),
                                @ColumnResult(name = "ticket_status", type = String.class)
                        },
                        targetClass = TicketWithUserName.class
                )})
public class TicketWithUserName {

    @Id
    @Column(name = "ticket_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ticketId;
    private String clientName;
    @Column(name = "tc.client_id")
    private Long clientId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd@HH:mm:ss")
    private LocalDateTime ticketTs;
    @Column(name = "ticket_status")
    private TicketStatus ticketStatus;

    public TicketWithUserName(Long ticketId, String clientName, Long clientId, LocalDateTime ticketTs, String ticketStatus) {
        this.ticketId = ticketId;
        this.clientName = clientName;
        this.clientId = clientId;
        this.ticketTs = ticketTs;
        this.ticketStatus = TicketStatus.valueOf(ticketStatus);
    }
}
