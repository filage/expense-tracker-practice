package by.filage.expensehistory.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

public record TransactionData(
        String id,
        String category,
        String type,
        BigDecimal amount,
        String description,
        LocalDate transactionDate,
        Instant createdAt
) {
}
