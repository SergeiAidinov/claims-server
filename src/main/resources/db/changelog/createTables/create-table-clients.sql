--liquibase formatted sql
--changeset sergei:3fa68a7a-87a0-4010-a8f3-5320c5e03c54

CREATE TABLE clients(
                        client_id BIGINT NOT NULL AUTO_INCREMENT,
                        client_name VARCHAR(45) NOT NULL,
                        PRIMARY KEY (client_id)
);

--rollback DROP TABLE IF EXISTS clients;