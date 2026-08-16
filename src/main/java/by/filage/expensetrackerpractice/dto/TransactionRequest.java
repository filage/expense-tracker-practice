package by.filage.expensetrackerpractice.dto;

import by.filage.expensetrackerpractice.entity.TransactionCategory;
import by.filage.expensetrackerpractice.entity.TransactionType;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TransactionRequest {
    @NotNull(message = "Category must not be null")
    private TransactionCategory category;

    @NotNull(message = "Type must not be null")
    private TransactionType type;

    @Positive
    @Digits(integer = 17, fraction = 2)
    @NotNull(message = "Amount must not be null")
    private BigDecimal amount;

    @Size(max = 500)
    private String description;

    @NotNull(message = "Date must not be null")
    private LocalDate transactionDate;

    public void setCategory(TransactionCategory category) {
        this.category = category;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTransactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
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
}
