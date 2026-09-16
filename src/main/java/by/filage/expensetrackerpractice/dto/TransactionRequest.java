package by.filage.expensetrackerpractice.dto;

import by.filage.expensetrackerpractice.entity.TransactionCategory;
import by.filage.expensetrackerpractice.entity.TransactionType;
import by.filage.expensetrackerpractice.validation.CategoryRequiresDescriptionValidation;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@CategoryRequiresDescriptionValidation
@Getter
@Setter
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
}
