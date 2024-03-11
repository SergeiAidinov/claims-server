package ru.yandex.incoming34.server.structures.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.yandex.incoming34.server.structures.TicketStatus;

@AllArgsConstructor
@Getter
public class TicketDraft {

    private final String text;
    private final TicketStatus ticketStatus = TicketStatus.DRAFT;
}
