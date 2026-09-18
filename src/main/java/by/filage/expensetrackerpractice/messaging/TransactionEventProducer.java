package by.filage.expensetrackerpractice.messaging;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TransactionEventProducer {
    private static final String TOPIC = "transaction-events";

    private final KafkaTemplate<String, String> kafkaTemplate;

    public void sendCreated(String transactionId) {
        kafkaTemplate.send(TOPIC, transactionId, transactionId);
    }
}
