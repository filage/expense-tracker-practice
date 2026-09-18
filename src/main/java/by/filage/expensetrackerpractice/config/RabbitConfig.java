package by.filage.expensetrackerpractice.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.core.Queue;

@Configuration
public class RabbitConfig {
    public static final String EXCHANGE = "transaction.events";
    public static final String DELETE_QUEUE = "transaction.deleted";
    public static final String DELETE_ROUTING_KEY = "transaction.deleted";

    @Bean
    public DirectExchange transactionExchange() {
        return new DirectExchange(EXCHANGE);
    }

    @Bean
    public Queue transactionDeletedQueue() {
        return QueueBuilder.durable(DELETE_QUEUE).build();
    }

    @Bean
    public Binding transactionDeletedBinding(Queue transactionDeletedQueue, DirectExchange transactionExchange) {
        return BindingBuilder.bind(transactionDeletedQueue).to(transactionExchange).with(DELETE_ROUTING_KEY);
    }
}
