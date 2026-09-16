package by.filage.expensetrackerpractice.entity.payment;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Entity
@Table(name = "card_payments")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CardPayment extends Payment {
    private String cardLastFourDigits;
    private String cardNetwork;

    public CardPayment(
            BigDecimal amount,
            LocalDate paymentDate,
            String description,
            String cardLastFourDigits,
            String cardNetwork
    ) {
        super(amount, paymentDate, description);
        this.cardLastFourDigits = cardLastFourDigits;
        this.cardNetwork = cardNetwork;
    }
}
