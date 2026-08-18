package by.filage.expensetrackerpractice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;
import java.util.Map;

@Getter
@AllArgsConstructor
public class ApiErrorResponse {
    private final String message;
    private final int status;
    private final Instant timestamp;
    private final String path;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private final Map<String, String> errors;
}
