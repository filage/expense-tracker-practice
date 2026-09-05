package by.filage.expensetrackerpractice.service;

import by.filage.expensetrackerpractice.dao.specification.TransactionSpecification;
import by.filage.expensetrackerpractice.dto.TransactionRequest;
import by.filage.expensetrackerpractice.dto.TransactionResponse;
import by.filage.expensetrackerpractice.entity.Transaction;
import by.filage.expensetrackerpractice.entity.TransactionType;
import by.filage.expensetrackerpractice.exception.TransactionNotFoundException;
import by.filage.expensetrackerpractice.exception.TransactionTypeMismatchException;
import by.filage.expensetrackerpractice.mapper.TransactionMapper;
import by.filage.expensetrackerpractice.repository.TransactionRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@AllArgsConstructor
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;
    private static final Set<String> ALLOWED_SORT_FIELD = Set.of("transactionDate", "amount", "type", "category", "createdAt");

    public TransactionResponse createTransaction(TransactionRequest request) {
        if (!request.getCategory().getAllowedType().equals(request.getType()))
            throw new TransactionTypeMismatchException();
        return transactionMapper.toResponse(transactionRepository.save(transactionMapper.toEntity(request)));
    }

    public Page<TransactionResponse> getAllTransactions(String sortBy, String order, int page, int size, TransactionType type, BigDecimal amount) {
        validateSortBy(sortBy);
        Sort.Direction sortDirection = Sort.Direction.fromString(order);
        Sort sort = Sort.by(sortDirection, sortBy);
        validatePagination(page, size);
        Pageable pageable = PageRequest.of(page, size, sort);
        Specification<Transaction> specification = ((root, query, criteriaBuilder) -> criteriaBuilder.conjunction());
        if(type != null)
            specification = specification.and(TransactionSpecification.hasType(type));
        if(amount != null)
            specification = specification.and(TransactionSpecification.hasMinAmount(amount));
        return transactionRepository.findAll(specification, pageable).map(transactionMapper::toResponse);
    }

    public TransactionResponse getTransactionById(UUID id) {
        return transactionMapper.toResponse(transactionRepository.findById(id).orElseThrow(() -> new TransactionNotFoundException(id)));
    }

    public TransactionResponse updateTransaction(TransactionRequest request, UUID id) {
        if (!request.getCategory().getAllowedType().equals(request.getType()))
            throw new TransactionTypeMismatchException();
        Transaction transaction = transactionRepository.findById(id).orElseThrow(() -> new TransactionNotFoundException(id));
        transaction.updateTransaction(request.getType(), request.getCategory(), request.getAmount(), request.getDescription(), request.getTransactionDate());
        return transactionMapper.toResponse(transactionRepository.save(transaction));
    }

    public void deleteTransaction(UUID id) {
        transactionRepository.delete(transactionRepository.findById(id).orElseThrow(() -> new TransactionNotFoundException(id)));
    }

    public List<Transaction> findByTypeAndMinAmount(TransactionType type, BigDecimal minAmount) {
        Specification<Transaction> specification = TransactionSpecification.hasType(type).and(TransactionSpecification.hasMinAmount(minAmount));
        return transactionRepository.findAll(specification);
    }

    public Page<Transaction> findByTypeAndMinAmount(TransactionType type, BigDecimal minAmount, int page, int size) {
        validatePagination(page, size);
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "transactionDate"));
        return transactionRepository.findByTypeAndAmountGreaterThanEqual(type, minAmount, pageable);
    }

    private void validatePagination(int page, int size) {
        if(page < 0)
            throw new IllegalArgumentException("Page must not be negative");
        if(size < 1 || size > 100)
            throw new IllegalArgumentException("Size must be between 1 and 100");
    }

    private void validateSortBy(String sortBy) {
        if(!ALLOWED_SORT_FIELD.contains(sortBy))
            throw new IllegalArgumentException("Unsupporetd sort type: " + sortBy);
    }
}
