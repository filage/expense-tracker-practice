package by.filage.expensehistory.service;

import by.filage.expensehistory.client.TransactionClient;
import by.filage.expensehistory.document.TransactionHistory;
import by.filage.expensehistory.dto.TransactionData;
import by.filage.expensehistory.dto.TransactionHistoryResponse;
import by.filage.expensehistory.repository.TransactionHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransactionHistoryService {
    private final TransactionHistoryRepository historyRepository;
    private final TransactionClient transactionClient;

    public TransactionData getTransaction(UUID transactionID) {
        try {
            return transactionClient.getTransaction(transactionID);
        } catch (HttpClientErrorException.NotFound exception) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Transaction " + transactionID + "not found in main service",
                    exception
            );
        }
    }

    public TransactionHistoryResponse saveHistory(UUID transactionId) {
        TransactionData transaction = getTransaction(transactionId);

        TransactionHistory history = new TransactionHistory(
                transactionId.toString(),
                transaction.category(),
                transaction.type(),
                transaction.amount(),
                transaction.description(),
                transaction.transactionDate(),
                transaction.createdAt(),
                Instant.now()
        );

        TransactionHistory savedHistory = historyRepository.save(history);
        return toResponse(savedHistory);
    }

    public TransactionHistoryResponse getHistory(UUID transactionId) {
        TransactionHistory history =
                historyRepository
                        .findFirstByTransactionIdOrderBySavedAtDesc(transactionId.toString())
                        .orElseThrow(() ->
                                new ResponseStatusException(
                                        HttpStatus.NOT_FOUND,
                                        "History for transaction " + transactionId + " not found"
                                )
                        );
        return toResponse(history);
    }

    private TransactionHistoryResponse toResponse(TransactionHistory history) {
        return new TransactionHistoryResponse(
                history.getId(),
                history.getTransactionId(),
                history.getCategory(),
                history.getType(),
                history.getAmount(),
                history.getDescription(),
                history.getTransactionDate(),
                history.getOriginalCreatedAt(),
                history.getSavedAt()
        );
    }
}
