--liquibase formatted sql
--changeset sergei:604b37d2-5aff-4d00-8a13-be0b72c0e526

CREATE TABLE emails
(
    client_id BIGINT,
    email     VARCHAR(126) UNIQUE NOT NULL check (email RLIKE '[a-zA-Z0-9]{1,60}@[a-zA-Z0-9]{1,60}\.[a-z,A-Z]{2,6}'),
    FOREIGN KEY (client_id) REFERENCES clients (client_id) ON DELETE CASCADE
);
--rollback DROP TABLE IF EXISTS emails;