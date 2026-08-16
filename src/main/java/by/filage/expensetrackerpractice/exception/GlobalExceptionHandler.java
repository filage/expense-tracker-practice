package by.filage.expensetrackerpractice.exception;

import by.filage.expensetrackerpractice.dto.ApiErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(TransactionNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleTransactionNotFoundException(TransactionNotFoundException exception, HttpServletRequest request) {
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(
                exception.getMessage(),
                HttpStatus.NOT_FOUND.value(),
                Instant.now(),
                request.getRequestURI(),
                Map.of()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiErrorResponse);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiErrorResponse> handleIllegalArgumentException(IllegalArgumentException exception, HttpServletRequest request) {
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(
                exception.getMessage(),
                HttpStatus.BAD_REQUEST.value(),
                Instant.now(),
                request.getRequestURI(),
                Map.of()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiErrorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleValidationException(MethodArgumentNotValidException exception, HttpServletRequest request) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError fieldError : exception.getBindingResult().getFieldErrors())
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(
                "Validation error",
                HttpStatus.BAD_REQUEST.value(),
                Instant.now(),
                request.getRequestURI(),
                errors
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiErrorResponse);
    }
}
