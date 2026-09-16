package by.filage.expensehistory.messaging;

import by.filage.expensehistory.config.RabbitConfig;
import by.filage.expensehistory.repository.TransactionHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TransactionDeletedListener {

    private final TransactionHistoryRepository historyRepository;

    @RabbitListener(queues = RabbitConfig.DELETE_QUEUE)
    public void handle(String transactionId) {
        historyRepository.deleteByTransactionId(transactionId);
    }
}