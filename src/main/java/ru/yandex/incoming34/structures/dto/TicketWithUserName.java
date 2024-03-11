package ru.yandex.incoming34.structures.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@NamedNativeQuery(name = "PostDtos", query = "SELECT client_name, ticket_id FROM tickets\n" +
        "            join tickets_db.table_clients tc on tc.client_id = tickets.client_id\n" +
        "            WHERE client_name = 'Winnie-the-Pooh'", resultSetMapping = "PostDtoMapping")
@SqlResultSetMapping(name = "PostDtoMapping",
        classes = {
                @ConstructorResult(
                        columns = {
                                @ColumnResult(name = "ticket_id", type = long.class),
                                @ColumnResult(name = "client_name")
                        },
                        targetClass = TicketWithUserName.class
                )})
public class TicketWithUserName {

    @Id
    @Column(name = "ticket_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ticketId;
    private String clientName;
    /*@Column(name = "client_id")
    private Long clientId;
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
