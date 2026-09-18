package by.filage.expensehistory.repository;

import by.filage.expensehistory.document.TransactionHistory;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface TransactionHistoryRepository extends MongoRepository<TransactionHistory, String> {
    Optional<TransactionHistory> findFirstByTransactionIdOrderBySavedAtDesc(String transactionId);

    void deleteByTransactionId(String transactionId);
}
