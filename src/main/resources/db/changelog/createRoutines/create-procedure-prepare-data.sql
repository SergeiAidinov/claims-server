CREATE PROCEDURE prepare_data(IN user_id INT)
BEGIN
    DECLARE i INT DEFAULT 1;
    DECLARE userName VARCHAR(128);
    SELECT client_name INTO userName FROM table_clients WHERE client_id = user_id;
    WHILE i <= 100 DO
            INSERT INTO tickets (ticket_id, client_id, ticket_text, ticket_status, ticket_ts)
            VALUES (null, user_id, CONCAT('Заявка ', userName, ' № ', i), 'DRAFT', DATE_SUB(DATE_SUB(CURRENT_TIMESTAMP, INTERVAL 1 DAY), INTERVAL i MINUTE));
            SET i = i + 1;
        END WHILE;
END;
