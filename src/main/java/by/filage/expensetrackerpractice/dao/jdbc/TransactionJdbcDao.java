package by.filage.expensetrackerpractice.dao.jdbc;

import by.filage.expensetrackerpractice.entity.Transaction;
import by.filage.expensetrackerpractice.entity.TransactionCategory;
import by.filage.expensetrackerpractice.entity.TransactionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class TransactionJdbcDao {
    private final DataSource dataSource;
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
        List<Transaction> transactions = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL)) {
            statement.setString(1, type.name());
            statement.setBigDecimal(2, minAmount);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    transactions.add(mapTransactionResult(resultSet));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return transactions;
    }

    private Transaction mapTransactionResult(ResultSet resultSet) throws SQLException{
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
