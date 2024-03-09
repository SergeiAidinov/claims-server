--liquibase formatted sql
--changeset sergei:50ea9b93-a6b3-4914-8663-42686712ccda

INSERT INTO clients values (NULL, 'Winnie-the-Pooh'), (NULL, 'Piglet'), (NULL, 'Eeyore');
INSERT INTO emails value (1, 'Winnie@yandex.ru'), (1, 'Pooh@yandex.ru'),
    (2, 'Piglet@gmail.com');
INSERT INTO phone_numbers values (1, '+79168132748'), (3, '+79268132746');

--rollback DELETE  FROM clients;