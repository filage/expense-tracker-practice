package by.filage.expensetrackerpractice.exception;

public class TransactionTypeMismatchException extends RuntimeException {
    public TransactionTypeMismatchException() {
        super("Category does not match transaction type");
    }
}
