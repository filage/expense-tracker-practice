package by.filage.expensehistory.messaging;

import by.filage.expensehistory.service.TransactionHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class TransactionEventListener {
    private final TransactionHistoryService historyService;

    @KafkaListener(topics = "transaction-events")
    public void handle(String transactionId) {
        historyService.saveHistory(UUID.fromString(transactionId));
    }
}
