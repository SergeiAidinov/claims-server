--liquibase formatted sql
--changeset sergei:258e4891-6f5a-48dc-8db6-ef3136922be7

CREATE PROCEDURE prepare_data_caller()
BEGIN
    DECLARE i INT DEFAULT 1;
    DECLARE userName VARCHAR(128);
    #SELECT client_name INTO userName FROM table_clients WHERE client_id = user_id;
    /*WHILE i <= 100 DO
            INSERT INTO tickets (ticket_id, client_id, ticket_text, ticket_status, ticket_ts)
            VALUES (null, user_id, CONCAT('Заявка ', userName, ' ', i), 'DRAFT', DATE_SUB(DATE_SUB(CURRENT_TIMESTAMP, INTERVAL 1 DAY), INTERVAL i MINUTE));

            SET i = i + 1;
        END WHILE;*/
END;

--rollback DROP PROCEDURE IF EXISTS prepare_data_caller;