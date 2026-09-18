--liquibase formatted sql

--changeset Maksim:005-create-budget-tables

CREATE TABLE budgets (
    id UUID PRIMARY KEY,
    budget_type VARCHAR(31) NOT NULL,
    name VARCHAR(255),
    limit_amount NUMERIC(19, 2),
    period_start DATE,
    period_end DATE,
    category VARCHAR(255),
    wallet_id UUID,
    CONSTRAINT fk_budgets_wallet
        FOREIGN KEY (wallet_id)
        REFERENCES wallets (id)
);