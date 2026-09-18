--liquibase formatted sql

--changeset Maksim:007-create-financial-alert-tables

CREATE TABLE budget_exceeded_alerts (
    id UUID PRIMARY KEY,
    message VARCHAR(255),
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    read BOOLEAN NOT NULL,
    budget_id UUID,
    exceeded_by NUMERIC(19, 2)
);

CREATE TABLE large_expense_alerts (
    id UUID PRIMARY KEY,
    message VARCHAR(255),
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    read BOOLEAN NOT NULL,
    transaction_id UUID,
    threshold_amount NUMERIC(19, 2)
);