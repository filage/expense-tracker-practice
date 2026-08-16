package by.filage.expensetrackerpractice.service;

import by.filage.expensetrackerpractice.dto.TransactionRequest;
import by.filage.expensetrackerpractice.dto.TransactionResponse;
import by.filage.expensetrackerpractice.entity.Transaction;
import by.filage.expensetrackerpractice.exception.TransactionNotFoundException;
import by.filage.expensetrackerpractice.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public TransactionResponse createTransaction(TransactionRequest request) {
        if (!request.getCategory().getAllowedType().equals(request.getType()))
            throw new IllegalArgumentException("Category does not match transaction type");
        Transaction transaction = new Transaction(request.getType(), request.getCategory(), request.getAmount(), request.getDescription(), request.getTransactionDate());
        Transaction readyTransaction = transactionRepository.save(transaction);
        return toResponse(readyTransaction);
    }

    public List<TransactionResponse> getAllTransactions() {
        List<Transaction> allTransactions = transactionRepository.findAll();
        List<TransactionResponse> allTransactionsResponse = new ArrayList<>();
        for (Transaction transaction : allTransactions) {
            allTransactionsResponse.add(toResponse(transaction));
        }
        return allTransactionsResponse;
    }

    public TransactionResponse getTransactionById(UUID id) {
        return toResponse(transactionRepository.findById(id).orElseThrow(() -> new TransactionNotFoundException(id)));
    }

    public TransactionResponse updateTransaction(TransactionRequest request, UUID id) {
        if (!request.getCategory().getAllowedType().equals(request.getType()))
            throw new IllegalArgumentException("Category does not match transaction type");
        Transaction transaction = transactionRepository.findById(id).orElseThrow(() -> new TransactionNotFoundException(id));
        transaction.updateTransaction(request.getType(), request.getCategory(), request.getAmount(), request.getDescription(), request.getTransactionDate());
        Transaction readyTransaction = transactionRepository.save(transaction);
        return toResponse(readyTransaction);
    }

    public void deleteTransaction(UUID id) {
        transactionRepository.delete(transactionRepository.findById(id).orElseThrow(() -> new TransactionNotFoundException(id)));
    }

    private TransactionResponse toResponse(Transaction transaction) {
        return new TransactionResponse(
                transaction.getId().toString(),
                transaction.getCategory(),
                transaction.getType(),
                transaction.getAmount(),
                transaction.getDescription(),
                transaction.getTransactionDate(),
                transaction.getCreatedAt()
        );
    }
}
