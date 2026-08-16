package by.filage.expensetrackerpractice.exception;

import java.util.UUID;

public class TransactionNotFoundException extends RuntimeException {
    public TransactionNotFoundException(UUID id) {
        super("Transaction with id: " + id + " not found");
    }
}
