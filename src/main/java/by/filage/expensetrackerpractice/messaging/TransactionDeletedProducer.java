package by.filage.expensetrackerpractice.messaging;


import by.filage.expensetrackerpractice.config.RabbitConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TransactionDeletedProducer {
    private final RabbitTemplate rabbitTemplate;

    public void sendDeleted(String transactionId) {
        rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE, RabbitConfig.DELETE_ROUTING_KEY, transactionId);
    }
}
