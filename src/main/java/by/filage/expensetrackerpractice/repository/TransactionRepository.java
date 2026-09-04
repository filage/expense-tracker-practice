package by.filage.expensetrackerpractice.repository;

import by.filage.expensetrackerpractice.entity.Transaction;
import by.filage.expensetrackerpractice.entity.TransactionType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transaction, UUID>, JpaSpecificationExecutor<Transaction> {
    List<Transaction> findByTypeAndAmountGreaterThanEqualOrderByTransactionDateDesc(TransactionType type, BigDecimal amount);

    Page<Transaction> findByTypeAndAmountGreaterThanEqual(TransactionType type, BigDecimal amount, Pageable pageable);

    @EntityGraph(attributePaths = "wallet")
    List<Transaction> findByTypeAndAmountGreaterThanEqual(TransactionType type, BigDecimal amount);
}
