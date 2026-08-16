package by.filage.expensetrackerpractice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "transactions")
public class Transaction {
    @Id
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionType type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionCategory category;

    @Positive
    @Digits(integer = 17, fraction = 2)
    @Column(nullable = false, scale = 2, precision = 19)
    private BigDecimal amount;

    @Column(length = 500)
    private String description;

    @Column(nullable = false)
    private LocalDate transactionDate;

    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    protected Transaction() {
    }

    public Transaction(TransactionType type, TransactionCategory category, BigDecimal amount, String description, LocalDate transactionDate) {
        id = UUID.randomUUID();
        this.type = type;
        this.category = category;
        this.amount = amount;
        this.description = description;
        this.transactionDate = transactionDate;
        this.createdAt = Instant.now();
    }

    public void updateTransaction(TransactionType type, TransactionCategory category, BigDecimal amount, String description, LocalDate transactionDate) {
        this.type = type;
        this.category = category;
        this.amount = amount;
        this.description = description;
        this.transactionDate = transactionDate;
    }

    public UUID getId() {
        return id;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public TransactionCategory getCategory() {
        return category;
    }

    public TransactionType getType() {
        return type;
    }
}
