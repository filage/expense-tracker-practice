package by.filage.expensetrackerpractice.dao.jdbctemplate;

import by.filage.expensetrackerpractice.entity.Transaction;
import by.filage.expensetrackerpractice.entity.TransactionCategory;
import by.filage.expensetrackerpractice.entity.TransactionType;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class TransactionJdbcTemplateDao {
    private final JdbcTemplate jdbcTemplate;
    private static final String SQL = """
                SELECT 
                    id,
                    type,
                    category,
                    amount,
                    description,
                    transaction_date,
                    created_at
                FROM transactions
                WHERE type = ?
                AND amount >= ?
                ORDER BY transaction_date DESC
                """;

    public List<Transaction> findByTypeAndMinAmount(TransactionType type, BigDecimal minAmount) {
        return jdbcTemplate.query(SQL, this::mapTransactionResult, type.name(), minAmount);
    }

    private Transaction mapTransactionResult(ResultSet resultSet, int rowNum) throws SQLException {
        return new Transaction(
                resultSet.getObject("id", UUID.class),
                TransactionType.valueOf(resultSet.getString("type")),
                TransactionCategory.valueOf(resultSet.getString("category")),
                resultSet.getBigDecimal("amount"),
                resultSet.getString("description"),
                resultSet.getObject("transaction_date", LocalDate.class),
                resultSet.getTimestamp("created_at").toInstant()
        );
    }
}
