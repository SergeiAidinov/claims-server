package ru.yandex.incoming34.structures.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.yandex.incoming34.structures.TicketStatus;

@AllArgsConstructor
@Getter
public class TicketDraft {

    private final String text;
    private final TicketStatus ticketStatus = TicketStatus.DRAFT;
}
