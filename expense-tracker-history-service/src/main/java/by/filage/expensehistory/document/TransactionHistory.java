package by.filage.expensehistory.document;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Document(collection = "transaction_history")
public class TransactionHistory {
    @Id
    private String id;

    private String transactionId;
    private String category;
    private String type;
    private BigDecimal amount;
    private String description;
    private LocalDate transactionDate;
    private Instant originalCreatedAt;
    private Instant savedAt;

    public TransactionHistory(
            String transactionId,
            String category,
            String type,
            BigDecimal amount,
            String description,
            LocalDate transactionDate,
            Instant originalCreatedAt,
            Instant savedAt
    ) {
        this.transactionId = transactionId;
        this.category = category;
        this.type = type;
        this.amount = amount;
        this.description = description;
        this.transactionDate = transactionDate;
        this.originalCreatedAt = originalCreatedAt;
        this.savedAt = savedAt;
    }
}
