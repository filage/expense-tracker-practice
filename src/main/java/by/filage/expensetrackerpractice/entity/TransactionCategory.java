package by.filage.expensetrackerpractice.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TransactionCategory {
    FOOD(TransactionType.EXPENSE),
    TRANSPORT(TransactionType.EXPENSE),
    HOUSING(TransactionType.EXPENSE),
    HEALTH(TransactionType.EXPENSE),
    ENTERTAINMENT(TransactionType.EXPENSE),
    CLOTHING(TransactionType.EXPENSE),

    SALARY(TransactionType.INCOME),
    FREELANCE(TransactionType.INCOME),
    GIFT(TransactionType.INCOME),
    INVESTMENT(TransactionType.INCOME),

    OTHER_INCOME(TransactionType.INCOME),
    OTHER_EXPENSE(TransactionType.EXPENSE);

    private final TransactionType allowedType;
}
