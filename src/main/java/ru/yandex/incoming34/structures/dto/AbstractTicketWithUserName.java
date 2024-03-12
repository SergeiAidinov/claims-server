package ru.yandex.incoming34.structures.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import ru.yandex.incoming34.structures.TicketStatus;

import javax.persistence.*;
import java.time.LocalDateTime;

/*@SqlResultSetMapping(name = "FindAllWithSimilarClientNameMapping",
        classes = {
                @ConstructorResult(
                        columns = {
                                @ColumnResult(name = "ticket_id", type = Long.class),
                                @ColumnResult(name = "client_name", type = String.class),
                                @ColumnResult(name = "tc.client_id", type = Long.class),
                                @ColumnResult(name = "ticket_ts", type = LocalDateTime.class),
                                @ColumnResult(name = "ticket_status", type = String.class)
                        },
                        targetClass = TicketWithUserNameAscending.class
                )})
@MappedSuperclass*/
@MappedSuperclass
public abstract class AbstractTicketWithUserName {

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

    public AbstractTicketWithUserName(Long ticketId, String clientName, Long clientId, LocalDateTime ticketTs, String ticketStatus) {
        this.clientName = clientName;
        this.clientId = clientId;
        this.ticketTs = ticketTs;
        this.ticketStatus = TicketStatus.valueOf(ticketStatus);
    }

    public AbstractTicketWithUserName(){};

    /*public AbstractTicketWithUserName(String clientName, Long clientId, LocalDateTime ticketTs, String ticketStatus) {
        this.clientName = clientName;
        this.clientId = clientId;
        this.ticketTs = ticketTs;
        this.ticketStatus = TicketStatus.valueOf(ticketStatus);
    }*/
}
