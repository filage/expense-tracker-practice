package by.filage.expensetrackerpractice.controller;

import by.filage.expensetrackerpractice.dto.TransactionResponse;
import by.filage.expensetrackerpractice.entity.TransactionCategory;
import by.filage.expensetrackerpractice.entity.TransactionType;
import by.filage.expensetrackerpractice.exception.GlobalExceptionHandler;
import by.filage.expensetrackerpractice.exception.TransactionNotFoundException;
import by.filage.expensetrackerpractice.exception.TransactionTypeMismatchException;
import by.filage.expensetrackerpractice.service.TransactionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Page;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TransactionController.class)
@Import(GlobalExceptionHandler.class)
public class TransactionControllerTest {
    private static final UUID TRANSACTION_ID = UUID.fromString("11111111-1111-1111-1111-111111111111");

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TransactionService transactionService;

    @Test
    void createsTransactionWhenRequestIsValid() throws Exception {
        when(transactionService.createTransaction(any())).thenReturn(response());

        mockMvc.perform(post("/api/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(validJson()))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id")
                        .value(TRANSACTION_ID.toString()))
                .andExpect(jsonPath("$.amount")
                        .value(25.50));
    }

    @Test
    void returnsBadRequestWhenCreateRequestIsInvalid() throws Exception {
        String json = """
                {
                  "type": "EXPENSE",
                  "category": "FOOD",
                  "description": "Lunch",
                  "transactionDate": "2026-09-13"
                }
                """;

        mockMvc.perform(post("/api/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message")
                        .value("Validation error"))
                .andExpect(jsonPath("$.errors.amount")
                        .value("Amount must not be null"));
    }

    @Test
    void returnsTransactionsWhenFiltersAreProvided() throws Exception {
        Page<TransactionResponse> page = new PageImpl<>(List.of(response()), PageRequest.of(0, 10), 1);

        when(transactionService.getAllTransactions(
                any(),
                eq(TransactionType.EXPENSE),
                eq(new BigDecimal("10"))
        )).thenReturn(page);

        mockMvc.perform(get("/api/transactions")
                        .param("page", "0")
                        .param("size", "10")
                        .param("sort", "amount,desc")
                        .param("type", "EXPENSE")
                        .param("amount", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id")
                        .value(TRANSACTION_ID.toString()))
                .andExpect(jsonPath("$.content[0].amount")
                        .value(25.50));
    }

    @Test
    void returnsTransactionWhenItExists() throws Exception {
        when(transactionService.getTransactionById(TRANSACTION_ID)).thenReturn(response());

        mockMvc.perform(get("/api/transactions/{id}", TRANSACTION_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id")
                        .value(TRANSACTION_ID.toString()));
    }

    @Test
    void returnsNotFoundWhenTransactionDoesNotExist() throws Exception {
        when(transactionService.getTransactionById(TRANSACTION_ID)).thenThrow(new TransactionNotFoundException(TRANSACTION_ID));

        mockMvc.perform(get("/api/transactions/{id}", TRANSACTION_ID))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status")
                        .value(404));
    }

    @Test
    void returnsBadRequestWhenTransactionIdIsInvalid() throws Exception {
        mockMvc.perform(get("/api/transactions/not-a-uuid"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status")
                        .value(400))
                .andExpect(jsonPath("$.message")
                        .value("Invalid parameter value: not-a-uuid"));
    }

    @Test
    void updatesExistingTransaction() throws Exception {
        when(transactionService.updateTransaction(any(), eq(TRANSACTION_ID))).thenReturn(response());

        mockMvc.perform(put("/api/transactions/{id}", TRANSACTION_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(validJson()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id")
                        .value(TRANSACTION_ID.toString()));
    }

    @Test
    void deletesExistingTransaction() throws Exception {
        mockMvc.perform(delete("/api/transactions/{id}", TRANSACTION_ID)).andExpect(status().isNoContent());

        verify(transactionService).deleteTransaction(TRANSACTION_ID);
    }

    @Test
    void returnsBadRequestWhenUpdateRequestIsInvalid() throws Exception {
        String json = """
                {
                  "type": "EXPENSE",
                  "category": "FOOD",
                  "description": "Lunch",
                  "transactionDate": "2026-09-13"
                }
                """;

        mockMvc.perform(put("/api/transactions/{id}", TRANSACTION_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message")
                        .value("Validation error"))
                .andExpect(jsonPath("$.errors.amount")
                        .value("Amount must not be null"));
    }

    @Test
    void returnsNotFoundWhenUpdatingMissingTransaction() throws Exception {
        when(transactionService.updateTransaction(any(), eq(TRANSACTION_ID)))
                .thenThrow(new TransactionNotFoundException(TRANSACTION_ID));

        mockMvc.perform(put("/api/transactions/{id}", TRANSACTION_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(validJson()))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status")
                        .value(404));
    }

    @Test
    void returnsBadRequestWhenUpdatingWithMismatchedTransactionType()
            throws Exception {
        when(transactionService.updateTransaction(any(), eq(TRANSACTION_ID)))
                .thenThrow(new TransactionTypeMismatchException());

        mockMvc.perform(put("/api/transactions/{id}", TRANSACTION_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(validJson()))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status")
                        .value(400));
    }

    @Test
    void returnsNotFoundWhenDeletingMissingTransaction() throws Exception {
        doThrow(new TransactionNotFoundException(TRANSACTION_ID))
                .when(transactionService)
                .deleteTransaction(TRANSACTION_ID);

        mockMvc.perform(delete("/api/transactions/{id}", TRANSACTION_ID))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status")
                        .value(404));
    }

    private String validJson() {
        return """
                {
                  "type": "EXPENSE",
                  "category": "FOOD",
                  "amount": 25.50,
                  "description": "Lunch",
                  "transactionDate": "2026-09-13"
                }
                """;
    }

    private TransactionResponse response() {
        return new TransactionResponse(
                TRANSACTION_ID.toString(),
                TransactionCategory.FOOD,
                TransactionType.EXPENSE,
                new BigDecimal("25.50"),
                "Lunch",
                LocalDate.of(2026, 9, 13),
                Instant.parse("2026-09-13T10:00:00Z")
        );
    }
}
