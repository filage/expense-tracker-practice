--liquibase formatted sql

--changeset Maksim:002-add-description

ALTER TABLE transactions
    ADD COLUMN description VARCHAR(500);