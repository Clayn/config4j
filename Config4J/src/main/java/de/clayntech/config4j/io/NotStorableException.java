package de.clayntech.config4j.io;

public class NotStorableException extends RuntimeException {
    public NotStorableException() {
    }

    public NotStorableException(String message) {
        super(message);
    }
}
