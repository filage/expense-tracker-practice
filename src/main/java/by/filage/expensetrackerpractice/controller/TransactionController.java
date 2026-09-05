package by.filage.expensetrackerpractice.controller;

import by.filage.expensetrackerpractice.dto.TransactionRequest;
import by.filage.expensetrackerpractice.dto.TransactionResponse;
import by.filage.expensetrackerpractice.entity.TransactionType;
import by.filage.expensetrackerpractice.service.TransactionService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.UUID;

@RestController
@RequestMapping("/api/transactions")
@AllArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TransactionResponse createTransaction(@Valid @RequestBody TransactionRequest request) {
        return transactionService.createTransaction(request);
    }

    @GetMapping
    public Page<TransactionResponse> getTransactions(
            @RequestParam(defaultValue = "transactionDate") String sortBy,
            @RequestParam(defaultValue = "DESC") String order,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) TransactionType type,
            @RequestParam(required = false) BigDecimal amount) {
        return transactionService.getAllTransactions(sortBy, order, page, size, type, amount);
    }

    @GetMapping("/{id}")
    public TransactionResponse getTransactionById(@PathVariable UUID id) {
        return transactionService.getTransactionById(id);
    }

    @PutMapping("/{id}")
    public TransactionResponse updateTransaction(@PathVariable UUID id, @Valid @RequestBody TransactionRequest request) {
        return transactionService.updateTransaction(request, id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTransaction(@PathVariable UUID id) {
        transactionService.deleteTransaction(id);
    }
}
