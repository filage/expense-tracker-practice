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
@Table(name = "large_expense_alerts")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LargeExpenseAlert extends FinancialAlert {

    private UUID transactionId;
    private BigDecimal thresholdAmount;

    public LargeExpenseAlert(
            String message,
            boolean read,
            UUID transactionId,
            BigDecimal thresholdAmount
    ) {
        super(message, read);
        this.transactionId = transactionId;
        this.thresholdAmount = thresholdAmount;
    }
}
