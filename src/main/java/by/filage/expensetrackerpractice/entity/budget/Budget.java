package by.filage.expensetrackerpractice.entity.budget;

import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Entity
@Table(name = "budgets")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "budget_type")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class Budget {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;
    private BigDecimal limitAmount;
    private LocalDate periodStart;
    private LocalDate periodEnd;

    protected Budget(String name, BigDecimal limitAmount, LocalDate periodStart, LocalDate periodEnd) {
        this.name = name;
        this.limitAmount = limitAmount;
        this.periodStart = periodStart;
        this.periodEnd = periodEnd;
    }
}
