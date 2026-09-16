package by.filage.expensetrackerpractice.entity.budget;

import by.filage.expensetrackerpractice.entity.TransactionCategory;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@DiscriminatorValue("CATEGORY")
public class CategoryBudget extends Budget{
    @Enumerated(EnumType.STRING)
    private TransactionCategory category;

    protected CategoryBudget() {
    }

    public CategoryBudget(
            String name,
            BigDecimal limitAmount,
            LocalDate periodStart,
            LocalDate periodEnd,
            TransactionCategory category
    ) {
        super(name, limitAmount, periodStart, periodEnd);
        this.category = category;
    }
}
