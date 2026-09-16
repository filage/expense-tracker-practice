package by.filage.expensetrackerpractice.entity.budget;

import by.filage.expensetrackerpractice.entity.Wallet;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Entity
@DiscriminatorValue("WALLET")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WalletBudget extends Budget{
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wallet_id")
    private Wallet wallet;

    public WalletBudget(
            String name,
            BigDecimal limitAmount,
            LocalDate periodStart,
            LocalDate periodEnd,
            Wallet wallet
    ) {
        super(name, limitAmount, periodStart, periodEnd);
        this.wallet = wallet;
    }
}
