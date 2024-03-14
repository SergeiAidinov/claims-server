package ru.yandex.incoming34.structures.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import ru.yandex.incoming34.structures.TicketStatus;

import javax.persistence.*;
import java.time.LocalDateTime;

@MappedSuperclass
public abstract class AbstractTicketWithUserName {

    @Id
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ticketId;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String clientName;
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Long clientId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd@HH:mm:ss")
    private LocalDateTime ticketTs;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private TicketStatus ticketStatus;

    public AbstractTicketWithUserName(Long ticketId, String clientName, Long clientId, LocalDateTime ticketTs, String ticketStatus) {
        this.ticketId = ticketId;
        this.clientName = clientName;
        this.clientId = clientId;
        this.ticketTs = ticketTs;
        this.ticketStatus = TicketStatus.valueOf(ticketStatus);
    }

    public AbstractTicketWithUserName() {
    };
}
