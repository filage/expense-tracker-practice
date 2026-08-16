package by.filage.expensetrackerpractice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.Instant;
import java.util.Map;

public class ApiErrorResponse {
    private final String message;
    private final int status;
    private final Instant timestamp;
    private final String path;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private final Map<String, String> errors;

    public ApiErrorResponse(String message, int status, Instant timestamp, String path, Map<String, String> errors) {
        this.message = message;
        this.status = status;
        this.timestamp = timestamp;
        this.path = path;
        this.errors = errors;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public int getStatus() {
        return status;
    }

    public String getPath() {
        return path;
    }

    public String getMessage() {
        return message;
    }

    public Map<String, String> getErrors() {
        return errors;
    }
}
