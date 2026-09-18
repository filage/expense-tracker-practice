package by.filage.expensetrackerpractice.entity.financialalert;


import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Entity
@Table(name = "budget_exceeded_alerts")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BudgetExceededAlert extends FinancialAlert {

    private UUID budgetId;
    private BigDecimal exceededBy;

    public BudgetExceededAlert(
            String message,
            boolean read,
            UUID budgetId,
            BigDecimal exceededBy
    ) {
        super(message, read);
        this.budgetId = budgetId;
        this.exceededBy = exceededBy;
    }
}
