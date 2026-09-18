--liquibase formatted sql

--changeset Maksim:006-create-payment-tables

CREATE TABLE payments (
    id UUID PRIMARY KEY,
    amount NUMERIC(19, 2),
    payment_date DATE,
    description VARCHAR(255)
);

CREATE TABLE card_payments (
    id UUID PRIMARY KEY,
    card_last_four_digits VARCHAR(255),
    card_network VARCHAR(255),
    CONSTRAINT fk_card_payments_payment
        FOREIGN KEY (id)
        REFERENCES payments (id)
);

CREATE TABLE bank_transfer_payments (
    id UUID PRIMARY KEY,
    bank_name VARCHAR(255),
    transfer_reference VARCHAR(255),
    CONSTRAINT fk_bank_transfer_payments_payment
        FOREIGN KEY (id)
        REFERENCES payments (id)
);