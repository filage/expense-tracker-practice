package by.filage.expensetrackerpractice.validation;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Set;

public class PaginationValidator {
    private static final Set<String> ALLOWED_SORT_FIELD = Set.of("transactionDate", "amount", "type", "category", "createdAt");

    public static void validateSortBy(Pageable pageable) {
        for (Sort.Order sortOrder : pageable.getSort()) {
            String field = sortOrder.getProperty();

            if (!ALLOWED_SORT_FIELD.contains(field)) {
                throw new IllegalArgumentException(
                        "Unsupported sort field: " + field
                );
            }
        }
    }

    public static void validatePagination(int page, int size) {
        if (page < 0) {
            throw new IllegalArgumentException(
                    "Page must not be negative"
            );
        }

        if (size < 1 || size > 100) {
            throw new IllegalArgumentException(
                    "Size must be between 1 and 100"
            );
        }
    }
}
