package by.filage.expensetrackerpractice.dto;

import by.filage.expensetrackerpractice.entity.TransactionCategory;
import by.filage.expensetrackerpractice.entity.TransactionType;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

public class TransactionResponse {
    private final String id;
    private final TransactionCategory category;
    private final TransactionType type;
    private final BigDecimal amount;
    private final String description;
    private final LocalDate transactionDate;
    private final Instant createdAt;

    public TransactionResponse(String id, TransactionCategory category, TransactionType type, BigDecimal amount, String description, LocalDate transactionDate, Instant createdAt) {
        this.id = id;
        this.category = category;
        this.type = type;
        this.amount = amount;
        this.description = description;
        this.transactionDate = transactionDate;
        this.createdAt = createdAt;
    }

    public String getId() {
        return id;
    }

    public TransactionCategory getCategory() {
        return category;
    }

    public TransactionType getType() {
        return type;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
}
