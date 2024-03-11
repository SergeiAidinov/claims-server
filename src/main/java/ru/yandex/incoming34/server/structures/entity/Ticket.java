package ru.yandex.incoming34.server.structures.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import ru.yandex.incoming34.server.structures.TicketStatus;
import ru.yandex.incoming34.server.structures.dto.TicketDraft;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "tickets")
@Getter
@NoArgsConstructor
@Setter
public class Ticket {

    @Id
    @Column(name = "ticket_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ticketId;
    @Column(name = "client_id")
    private Long clientId;
    @Column(name = "ticket_text")
    private String text;
    @Column(name = "ticket_status")
    @Enumerated(EnumType.STRING)
    private TicketStatus ticketStatus;
    @Column(name = "ticket_ts")
    @CreationTimestamp
    private Timestamp creationDate;

    public Ticket(Long clientId, String ticketDraftText) {
        this.clientId = clientId;
        text = ticketDraftText;
        ticketStatus = TicketStatus.DRAFT;
    }
}
