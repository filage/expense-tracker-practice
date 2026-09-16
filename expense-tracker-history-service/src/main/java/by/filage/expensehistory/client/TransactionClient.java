package by.filage.expensehistory.client;

import by.filage.expensehistory.dto.TransactionData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Component
public class TransactionClient {
    private final RestTemplate restTemplate;
    private final String mainServiceBaseUrl;

    public TransactionClient(RestTemplate restTemplate, @Value("${main-service.base-url}") String mainServiceBaseUrl) {
        this.restTemplate = restTemplate;
        this.mainServiceBaseUrl = mainServiceBaseUrl;
    }

    public TransactionData getTransaction(UUID transactionId) {
        return restTemplate.getForObject(mainServiceBaseUrl + "/api/transactions/{id}", TransactionData.class, transactionId);
    }
}
