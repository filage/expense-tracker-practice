package by.filage.expensetrackerpractice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Entity
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wallet_id")
    private Wallet wallet;

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

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    protected Transaction() {
    }

    @Builder
    public Transaction(UUID id, TransactionType type, TransactionCategory category, BigDecimal amount, String description, LocalDate transactionDate, Instant createdAt) {
        this.id = id;
        this.type = type;
        this.category = category;
        this.amount = amount;
        this.description = description;
        this.transactionDate = transactionDate;
        this.createdAt = createdAt;
    }

    public void updateTransaction(TransactionType type, TransactionCategory category, BigDecimal amount, String description, LocalDate transactionDate) {
        this.type = type;
        this.category = category;
        this.amount = amount;
        this.description = description;
        this.transactionDate = transactionDate;
    }
}
