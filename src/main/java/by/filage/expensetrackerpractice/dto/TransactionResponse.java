package by.filage.expensetrackerpractice.dto;

import by.filage.expensetrackerpractice.entity.TransactionCategory;
import by.filage.expensetrackerpractice.entity.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class TransactionResponse {
    private final String id;
    private final TransactionCategory category;
    private final TransactionType type;
    private final BigDecimal amount;
    private final String description;
    private final LocalDate transactionDate;
    private final Instant createdAt;
}
