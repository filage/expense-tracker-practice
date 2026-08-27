package by.filage.expensetrackerpractice.controller;

import by.filage.expensetrackerpractice.dto.TransactionRequest;
import by.filage.expensetrackerpractice.dto.TransactionResponse;
import by.filage.expensetrackerpractice.service.TransactionService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
    public List<TransactionResponse> getTransactions() {
        return transactionService.getAllTransactions();
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
