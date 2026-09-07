package by.filage.expensetrackerpractice.validation;

import by.filage.expensetrackerpractice.dto.TransactionRequest;
import by.filage.expensetrackerpractice.entity.TransactionCategory;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CategoryRequiresDescriptionValidator implements ConstraintValidator<CategoryRequiresDescriptionValidation, TransactionRequest> {
    @Override
    public boolean isValid(TransactionRequest request, ConstraintValidatorContext context) {
        if(request.getCategory() != TransactionCategory.OTHER_EXPENSE && request.getCategory() != TransactionCategory.OTHER_INCOME) {
            return true;
        }
        return request.getDescription() != null && !request.getDescription().isBlank();
    }
}
