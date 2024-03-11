--liquibase formatted sql
--changeset sergei:604b37d2-5aff-4d00-8a13-be0b72c0e526

CREATE TABLE tickets
(   ticket_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    client_id BIGINT,
    ticket_text    VARCHAR(1024),
    ticket_status enum ('DRAFT', 'FILED', 'ACCEPTED', 'DECLINRD'),
    FOREIGN KEY (client_id) REFERENCES table_clients (client_id) ON DELETE CASCADE
);

--rollback DROP TABLE IF EXISTS tickets;