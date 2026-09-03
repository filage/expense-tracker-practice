package by.filage.expensetrackerpractice.dao.jpql;

import by.filage.expensetrackerpractice.entity.Transaction;
import by.filage.expensetrackerpractice.entity.TransactionType;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class TransactionJpqlDao {
    private final EntityManager entityManager;

    private static final String JPQL = """
        SELECT t
        FROM Transaction t
        WHERE t.type = :type
        AND t.amount >= :minAmount
        ORDER BY t.transactionDate DESC
        """;

    public List<Transaction> findByTypeAndMinAmount(TransactionType type, BigDecimal minAmount) {
        return entityManager.createQuery(JPQL, Transaction.class)
                .setParameter("minAmount", minAmount)
                .setParameter("type", type)
                .getResultList();
    }


}
