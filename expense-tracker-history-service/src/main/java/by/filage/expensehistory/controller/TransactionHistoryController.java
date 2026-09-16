package by.filage.expensehistory.controller;


import by.filage.expensehistory.dto.TransactionHistoryResponse;
import by.filage.expensehistory.service.TransactionHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/history")
@RequiredArgsConstructor
public class TransactionHistoryController {
    private final TransactionHistoryService historyService;

    @PostMapping("/transactions/{transactionId}")
    @ResponseStatus(HttpStatus.CREATED)
    public TransactionHistoryResponse saveHistory(@PathVariable UUID transactionId) {
        return historyService.saveHistory(transactionId);
    }

    @GetMapping("/transactions/{transactionId}")
    public TransactionHistoryResponse getHistory(@PathVariable UUID transactionId) {
        return historyService.getHistory(transactionId);
    }
}
