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
@Table(name = "bank_transfer_payments")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BankTransferPayment extends Payment {
    private String bankName;
    private String transferReference;

    public BankTransferPayment(
            BigDecimal amount,
            LocalDate paymentDate,
            String description,
            String bankName,
            String transferReference
    ) {
        super(amount, paymentDate, description);
        this.bankName = bankName;
        this.transferReference = transferReference;
    }
}
