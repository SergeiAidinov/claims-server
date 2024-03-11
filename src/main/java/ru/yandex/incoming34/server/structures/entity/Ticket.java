package ru.yandex.incoming34.server.structures.entity;

import lombok.Getter;
import ru.yandex.incoming34.server.structures.TicketStatus;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tickets")
@Getter
public class Ticket {

    @Id
    @Column(name = "ticket_id")
    private String ticketId;
    @Column(name = "client_id")
    private String clientId;
    @Column(name = "ticket_text")
    private String text;
    @Column(name = "ticket_status")
    private TicketStatus ticketStatus;
}
