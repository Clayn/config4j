package de.clayntech.config4j;

public class ValueParsingException extends RuntimeException {
    public ValueParsingException() {
    }

    public ValueParsingException(String message) {
        super(message);
    }

    public ValueParsingException(String message, Throwable cause) {
        super(message, cause);
    }

    public ValueParsingException(Throwable cause) {
        super(cause);
    }
}
