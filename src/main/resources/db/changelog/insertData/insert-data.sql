--liquibase formatted sql
--changeset sergei:50ea9b93-a6b3-4914-8663-42686712ccda

INSERT INTO table_clients values (NULL, 'Winnie-the-Pooh', 'Winnie', '1234', 'USER:OPERATOR'); #, (NULL, 'Piglet'), (NULL, 'Eeyore');

--rollback DELETE  FROM table_clients;