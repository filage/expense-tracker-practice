package by.filage.expensetrackerpractice.service;

import by.filage.expensetrackerpractice.dto.TransactionRequest;
import by.filage.expensetrackerpractice.dto.TransactionResponse;
import by.filage.expensetrackerpractice.entity.Transaction;
import by.filage.expensetrackerpractice.entity.TransactionCategory;
import by.filage.expensetrackerpractice.entity.TransactionType;
import by.filage.expensetrackerpractice.exception.TransactionNotFoundException;
import by.filage.expensetrackerpractice.exception.TransactionTypeMismatchException;
import by.filage.expensetrackerpractice.mapper.TransactionMapper;
import by.filage.expensetrackerpractice.messaging.TransactionDeletedProducer;
import by.filage.expensetrackerpractice.messaging.TransactionEventProducer;
import by.filage.expensetrackerpractice.repository.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {
    private static final UUID TRANSACTION_ID = UUID.fromString("11111111-1111-1111-1111-111111111111");

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private TransactionMapper transactionMapper;

    @InjectMocks
    private TransactionService transactionService;

    @Mock
    private TransactionEventProducer transactionEventProducer;

    @Mock
    private TransactionDeletedProducer transactionDeletedProducer;

    @Test
    void createsTransactionAndPublishesEventWhenRequestIsValid() {
        TransactionRequest request = validRequest();
        Transaction transaction = transaction();
        TransactionResponse expected = response();

        when(transactionMapper.toEntity(request)).thenReturn(transaction);
        when(transactionRepository.save(transaction)).thenReturn(transaction);
        when(transactionMapper.toResponse(transaction)).thenReturn(expected);

        TransactionResponse actual = transactionService.createTransaction(request);

        assertThat(actual).isSameAs(expected);

        verify(transactionMapper).toEntity(request);
        verify(transactionRepository).save(transaction);
        verify(transactionMapper).toResponse(transaction);
        verify(transactionEventProducer).sendCreated(expected.getId());
    }

    @Test
    void throwsExceptionWhenTransactionTypeDoesNotMatchOnCreate() {
        TransactionRequest request = validRequest();
        request.setType(TransactionType.INCOME);

        assertThatThrownBy(() ->
                transactionService.createTransaction(request))
                .isInstanceOf(TransactionTypeMismatchException.class);

        verifyNoInteractions(transactionRepository, transactionMapper, transactionEventProducer);
    }

    @Test
    void returnsTransactionWhenItExists() {
        Transaction transaction = transaction();
        TransactionResponse expected = response();

        when(transactionRepository.findById(TRANSACTION_ID)).thenReturn(Optional.of(transaction));
        when(transactionMapper.toResponse(transaction)).thenReturn(expected);

        TransactionResponse actual = transactionService.getTransactionById(TRANSACTION_ID);

        assertThat(actual).isSameAs(expected);

        verify(transactionRepository).findById(TRANSACTION_ID);
        verify(transactionMapper).toResponse(transaction);
    }

    @Test
    void throwsExceptionWhenTransactionDoesNotExist() {
        when(transactionRepository.findById(TRANSACTION_ID)).thenReturn(Optional.empty());

        assertThatThrownBy(() ->
                transactionService.getTransactionById(TRANSACTION_ID))
                .isInstanceOf(TransactionNotFoundException.class)
                .hasMessageContaining(TRANSACTION_ID.toString());

        verify(transactionRepository).findById(TRANSACTION_ID);
        verify(transactionMapper, never()).toResponse(any());
    }

    @Test
    void updatesExistingTransaction() {
        TransactionRequest request = validRequest();
        request.setDescription("Updated description");

        Transaction transaction = transaction();
        TransactionResponse expected = response();

        when(transactionRepository.findById(TRANSACTION_ID)).thenReturn(Optional.of(transaction));
        when(transactionRepository.save(transaction)).thenReturn(transaction);
        when(transactionMapper.toResponse(transaction)).thenReturn(expected);

        TransactionResponse actual = transactionService.updateTransaction(request, TRANSACTION_ID);

        assertThat(actual).isSameAs(expected);
        assertThat(transaction.getDescription()).isEqualTo("Updated description");

        verify(transactionRepository).findById(TRANSACTION_ID);
        verify(transactionRepository).save(transaction);
        verify(transactionMapper).toResponse(transaction);
    }

    @Test
    void deletesExistingTransactionAndPublishesEvent() {
        Transaction transaction = transaction();

        when(transactionRepository.findById(TRANSACTION_ID)).thenReturn(Optional.of(transaction));

        transactionService.deleteTransaction(TRANSACTION_ID);

        verify(transactionRepository).findById(TRANSACTION_ID);
        verify(transactionRepository).delete(transaction);
        verify(transactionDeletedProducer).sendDeleted(TRANSACTION_ID.toString());
    }

    @Test
    void returnsAllTransactionsWithoutFilters() {
        Pageable pageable = PageRequest.of(0, 10);

        Transaction transaction = transaction();
        TransactionResponse expected = response();

        Page<Transaction> page = new PageImpl<>(List.of(transaction), pageable, 1L);

        when(transactionRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(page);

        when(transactionMapper.toResponse(transaction)).thenReturn(expected);

        Page<TransactionResponse> actual =
                transactionService.getAllTransactions(
                        pageable,
                        null,
                        null
                );

        assertThat(actual.getContent()).containsExactly(expected);
    }

    @Test
    void throwsExceptionWhenTransactionTypeDoesNotMatchOnUpdate() {
        TransactionRequest request = validRequest();
        request.setType(TransactionType.INCOME);

        assertThatThrownBy(() ->
                transactionService.updateTransaction(request, TRANSACTION_ID))
                .isInstanceOf(TransactionTypeMismatchException.class);

        verifyNoInteractions(
                transactionRepository,
                transactionMapper,
                transactionEventProducer,
                transactionDeletedProducer
        );
    }

    @Test
    void returnsFilteredTransactionsWhenTypeAndAmountAreProvided() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "amount"));

        Transaction transaction = transaction();
        TransactionResponse expected = response();
        Page<Transaction> page = new PageImpl<>(
                List.of(transaction),
                pageable,
                1L
        );

        when(transactionRepository.findAll(any(Specification.class), eq(pageable))).thenReturn(page);
        when(transactionMapper.toResponse(transaction)).thenReturn(expected);

        Page<TransactionResponse> actual =
                transactionService.getAllTransactions(
                        pageable,
                        TransactionType.EXPENSE,
                        new BigDecimal("10.00")
                );

        assertThat(actual.getContent()).containsExactly(expected);

        verify(transactionRepository).findAll(any(Specification.class), eq(pageable));
        verify(transactionMapper).toResponse(transaction);
    }

    private TransactionRequest validRequest() {
        TransactionRequest request = new TransactionRequest();
        request.setType(TransactionType.EXPENSE);
        request.setCategory(TransactionCategory.FOOD);
        request.setAmount(new BigDecimal("25.50"));
        request.setDescription("CyberLunch");
        request.setTransactionDate(LocalDate.of(2077, 9, 13));
        return request;
    }

    private Transaction transaction() {
        return Transaction.builder()
                .id(TRANSACTION_ID)
                .type(TransactionType.EXPENSE)
                .category(TransactionCategory.FOOD)
                .amount(new BigDecimal("25.50"))
                .description("CyberLunch")
                .transactionDate(LocalDate.of(2077, 9, 13))
                .createdAt(Instant.parse("2077-09-13T10:00:00Z"))
                .build();
    }

    private TransactionResponse response() {
        return new TransactionResponse(
                TRANSACTION_ID.toString(),
                TransactionCategory.FOOD,
                TransactionType.EXPENSE,
                new BigDecimal("25.50"),
                "CyberLunch",
                LocalDate.of(2077, 9, 13),
                Instant.parse("2077-09-13T10:00:00Z")
        );
    }
}
