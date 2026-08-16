package by.filage.expensetrackerpractice.controller;

import by.filage.expensetrackerpractice.dto.TransactionRequest;
import by.filage.expensetrackerpractice.dto.TransactionResponse;
import by.filage.expensetrackerpractice.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {
    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    public ResponseEntity<TransactionResponse> createTransaction(@Valid @RequestBody TransactionRequest request) {
        TransactionResponse response = transactionService.createTransaction(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<TransactionResponse>> getTransactions() {
        List<TransactionResponse> response = transactionService.getAllTransactions();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionResponse> getTransactionById(@PathVariable UUID id) {
        TransactionResponse response = transactionService.getTransactionById(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TransactionResponse> updateTransaction(@Valid @RequestBody TransactionRequest request, @PathVariable UUID id) {
        TransactionResponse response = transactionService.updateTransaction(request, id);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable UUID id) {
        transactionService.deleteTransaction(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }
}
