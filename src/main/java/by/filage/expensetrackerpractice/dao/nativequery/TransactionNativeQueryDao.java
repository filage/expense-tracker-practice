package by.filage.expensetrackerpractice.dao.nativequery;

import by.filage.expensetrackerpractice.entity.Transaction;
import by.filage.expensetrackerpractice.entity.TransactionType;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class TransactionNativeQueryDao {
    private static final String SQL = """
                SELECT 
                    id,
                    wallet_id,
                    type,
                    category,
                    amount,
                    description,
                    transaction_date,
                    created_at
                FROM transactions
                WHERE type = :type
                AND amount >= :minAmount
                ORDER BY transaction_date DESC
                """;

    private final EntityManager entityManager;

    public List<Transaction> findByTypeAndMinAmount(TransactionType type, BigDecimal minAmount) {
        return (List<Transaction>) entityManager
                .createNativeQuery(SQL, Transaction.class)
                .setParameter("minAmount", minAmount)
                .setParameter("type", type.name())
                .getResultList();
    }
}
