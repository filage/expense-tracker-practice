package by.filage.expensetrackerpractice.service;

import by.filage.expensetrackerpractice.dto.TransactionRequest;
import by.filage.expensetrackerpractice.dto.TransactionResponse;
import by.filage.expensetrackerpractice.entity.Transaction;
import by.filage.expensetrackerpractice.exception.TransactionNotFoundException;
import by.filage.expensetrackerpractice.mapper.TransactionMapper;
import by.filage.expensetrackerpractice.repository.TransactionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;

    public TransactionResponse createTransaction(TransactionRequest request) {
        if (!request.getCategory().getAllowedType().equals(request.getType()))
            throw new IllegalArgumentException("Category does not match transaction type");
        return transactionMapper.toResponse(transactionRepository.save(transactionMapper.toEntity(request)));
    }

    public List<TransactionResponse> getAllTransactions() {
        return transactionMapper.toResponseList(transactionRepository.findAll());
    }

    public TransactionResponse getTransactionById(UUID id) {
        return transactionMapper.toResponse(transactionRepository.findById(id).orElseThrow(() -> new TransactionNotFoundException(id)));
    }

    public TransactionResponse updateTransaction(TransactionRequest request, UUID id) {
        if (!request.getCategory().getAllowedType().equals(request.getType()))
            throw new IllegalArgumentException("Category does not match transaction type");
        Transaction transaction = transactionRepository.findById(id).orElseThrow(() -> new TransactionNotFoundException(id));
        transaction.updateTransaction(request.getType(), request.getCategory(), request.getAmount(), request.getDescription(), request.getTransactionDate());
        return transactionMapper.toResponse(transactionRepository.save(transaction));
    }

    public void deleteTransaction(UUID id) {
        transactionRepository.delete(transactionRepository.findById(id).orElseThrow(() -> new TransactionNotFoundException(id)));
    }
}
