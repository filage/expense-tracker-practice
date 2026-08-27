package by.filage.expensetrackerpractice.entity;

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

    TransactionCategory(TransactionType allowedType) {
        this.allowedType = allowedType;
    }

    public TransactionType getAllowedType() {
        return allowedType;
    }
}
