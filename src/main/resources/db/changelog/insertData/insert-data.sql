--liquibase formatted sql
--changeset sergei:50ea9b93-a6b3-4914-8663-42686712ccda

INSERT INTO table_clients values (NULL, 'Winnie-the-Pooh', 'winnie', '1234', 'USER:ADMIN:OPERATOR'),
                                 (NULL, 'Eeyore', 'eyeore', 'asdf', 'USER:ADMIN'),
                                 (NULL, 'Owl', 'owl', 'zxcv', 'USER:OPERATOR'),
                                 (NULL, 'Piglet', 'piglet', 'qwer', 'USER')
                                 ;

--rollback DELETE FROM table_clients;