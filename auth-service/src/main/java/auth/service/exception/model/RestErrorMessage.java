package auth.service.exception.model;

import org.springframework.http.HttpStatus;

public record RestErrorMessage(int status, HttpStatus error, String message) {
}
