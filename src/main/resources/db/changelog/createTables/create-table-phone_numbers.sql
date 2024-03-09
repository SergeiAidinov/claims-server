--liquibase formatted sql
--changeset sergei:01f3870b-a432-4952-b7bd-7255da6b98df

CREATE TABLE phone_numbers(
                        client_id BIGINT,
                        phone_number VARCHAR(12) UNIQUE NOT NULL check(phone_numbers.phone_number RLIKE'[+]{1}[0-9]{11}'),
                        FOREIGN KEY (client_id) REFERENCES clients (client_id) ON DELETE CASCADE
);

--rollback DROP TABLE IF EXISTS phone_numbers;