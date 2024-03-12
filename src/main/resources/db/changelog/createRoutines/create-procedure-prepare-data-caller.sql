CREATE PROCEDURE prepare_data_caller()

BEGIN
    DECLARE current_status BIGINT;
    DECLARE cursor_List_isdone BOOLEAN DEFAULT FALSE;
    DECLARE  client_id_cursor CURSOR FOR SELECT client_id FROM table_clients WHERE table_clients.client_roles LIKE '%ADMIN%';
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET cursor_List_isdone = TRUE;
    OPEN client_id_cursor;
    loop_List: LOOP
        FETCH client_id_cursor INTO current_status;
        IF cursor_List_isdone THEN
            LEAVE loop_List;
        END IF;
        CALL prepare_data(current_status);
    END LOOP;
    CLOSE client_id_cursor;
END
