package by.filage.expensetrackerpractice.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CategoryRequiresDescriptionValidator.class)
public @interface CategoryRequiresDescriptionValidation {
    String message() default "Description is required for OTHER_INCOME and OTHER_EXPENSE";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
