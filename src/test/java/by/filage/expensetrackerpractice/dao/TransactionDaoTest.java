package by.filage.expensetrackerpractice.dao;

import by.filage.expensetrackerpractice.dao.jdbc.TransactionJdbcDao;
import by.filage.expensetrackerpractice.dao.jdbctemplate.TransactionJdbcTemplateDao;
import by.filage.expensetrackerpractice.dao.jpql.TransactionJpqlDao;
import by.filage.expensetrackerpractice.dao.nativequery.TransactionNativeQueryDao;
import by.filage.expensetrackerpractice.entity.Transaction;
import by.filage.expensetrackerpractice.entity.TransactionCategory;
import by.filage.expensetrackerpractice.entity.TransactionType;
import by.filage.expensetrackerpractice.repository.TransactionRepository;
import by.filage.expensetrackerpractice.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@SpringBootTest
class TransactionDaoTest {

    @Container
    static final PostgreSQLContainer<?> POSTGRES = new PostgreSQLContainer<>("postgres:18")
            .withDatabaseName("expense_tracker")
            .withUsername("postgres")
            .withPassword("postgres");

    @DynamicPropertySource
    static void configureDatabase(DynamicPropertyRegistry registry) {
        registry.add("DB_URL", POSTGRES::getJdbcUrl);
        registry.add("DB_USERNAME", POSTGRES::getUsername);
        registry.add("DB_PASSWORD", POSTGRES::getPassword);
    }

    @Autowired
    private TransactionJdbcDao jdbcDao;

    @Autowired
    private TransactionJdbcTemplateDao jdbcTemplateDao;

    @Autowired
    private TransactionJpqlDao jpqlDao;

    @Autowired
    private TransactionNativeQueryDao nativeQueryDao;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private TransactionService transactionService;

    @BeforeEach
    void setUp() {
        transactionRepository.deleteAll();

        transactionRepository.saveAll(List.of(
                transaction(TransactionType.EXPENSE, new BigDecimal("150.00"), LocalDate.of(2026, 9, 10)),
                transaction(TransactionType.EXPENSE, new BigDecimal("120.00"), LocalDate.of(2026, 9, 11)),
                transaction(TransactionType.EXPENSE, new BigDecimal("50.00"), LocalDate.of(2026, 9, 12)),
                transaction(TransactionType.INCOME, new BigDecimal("500.00"), LocalDate.of(2026, 9, 13))
        ));

        transactionRepository.flush();
    }

    @Test
    void jdbc() {
        List<Transaction> result = jdbcDao.findByTypeAndMinAmount(TransactionType.EXPENSE, new BigDecimal("100.00"));

        assertFiltered(result);
    }

    @Test
    void jdbcTemplate() {
        List<Transaction> result = jdbcTemplateDao.findByTypeAndMinAmount(TransactionType.EXPENSE, new BigDecimal("100.00"));

        assertFiltered(result);
    }

    @Test
    void jpql() {
        List<Transaction> result = jpqlDao.findByTypeAndMinAmount(TransactionType.EXPENSE, new BigDecimal("100.00"));

        assertFiltered(result);
    }

    @Test
    void nativeQuery() {
        List<Transaction> result = nativeQueryDao.findByTypeAndMinAmount(TransactionType.EXPENSE, new BigDecimal("100.00"));

        assertFiltered(result);
    }

    @Test
    void springDataJpa() {
        List<Transaction> result = transactionRepository
                        .findByTypeAndAmountGreaterThanEqualOrderByTransactionDateDesc(
                                TransactionType.EXPENSE,
                                new BigDecimal("100.00")
                        );

        assertFiltered(result);
    }

    @Test
    void specification() {
        List<Transaction> result = transactionService.findByTypeAndMinAmount(TransactionType.EXPENSE, new BigDecimal("100.00"));

        assertThat(result)
                .extracting(Transaction::getAmount)
                .containsExactlyInAnyOrder(new BigDecimal("150.00"), new BigDecimal("120.00"));
    }

    @Test
    void pagination() {
        Page<Transaction> result =
                transactionService.findByTypeAndMinAmount(
                        TransactionType.EXPENSE,
                        new BigDecimal("100.00"),
                        0,
                        1
                );

        assertThat(result.getContent()).hasSize(1);

        assertThat(result.getTotalElements()).isEqualTo(2);
    }

    private void assertFiltered(List<Transaction> transactions) {
        assertThat(transactions)
                .extracting(Transaction::getAmount)
                .containsExactly(new BigDecimal("120.00"), new BigDecimal("150.00"));
    }

    private Transaction transaction(TransactionType type, BigDecimal amount, LocalDate date) {
        return Transaction.builder()
                .type(type)
                .category(type == TransactionType.EXPENSE
                        ? TransactionCategory.FOOD
                        : TransactionCategory.SALARY)
                .amount(amount)
                .description("DAO test")
                .transactionDate(date)
                .build();
    }
}