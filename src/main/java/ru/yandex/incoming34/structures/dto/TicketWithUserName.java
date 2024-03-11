package ru.yandex.incoming34.structures.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@NamedNativeQuery(name = "PostDtos", query = "SELECT client_name, ticket_id, tc.client_id, ticket_ts FROM tickets\n" +
        "            join tickets_db.table_clients tc on tc.client_id = tickets.client_id\n" +
        "            WHERE client_name = :cn", resultSetMapping = "PostDtoMapping")
@SqlResultSetMapping(name = "PostDtoMapping",
        classes = {
                @ConstructorResult(
                        columns = {
                                @ColumnResult(name = "ticket_id", type = Long.class),
                                @ColumnResult(name = "client_name", type = String.class),
                                @ColumnResult(name = "tc.client_id", type = Long.class),
                                @ColumnResult(name = "ticket_ts", type = LocalDateTime.class)
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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern =  "yyyy-MM-dd@HH:mm:ss")
    private LocalDateTime ticketTs;
   /* @Column(name = "ticket_ts")
    */
    /*
    @Column(name = "ticket_id")
    private Long ticketId;
    @Column(name = "ticket_text")
    private String text;
    @Column(name = "ticket_status")
    @Enumerated(EnumType.STRING)
    private TicketStatus ticketStatus;
    @Column(name = "ticket_ts")
    @CreationTimestamp
    private Timestamp creationDate;*/
}
