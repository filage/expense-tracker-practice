--liquibase formatted sql

--changeset Maksim:003-insert-test-data

INSERT INTO transactions (
    id,
    type,
    category,
    amount,
    description,
    transaction_date,
    created_at
)
VALUES (
           '11111111-1111-1111-1111-111111111111',
           'EXPENSE',
           'FOOD',
           25.50,
           'Обед в кафе',
           DATE '2026-08-25',
           CURRENT_TIMESTAMP
       );
INSERT INTO transactions (
    id,
    type,
    category,
    amount,
    description,
    transaction_date,
    created_at
)
VALUES (
           '22222222-2222-2222-2222-222222222222',
           'INCOME',
           'SALARY',
           2500.00,
           'Зарплата с работы',
           DATE '2026-08-15',
           CURRENT_TIMESTAMP
       );