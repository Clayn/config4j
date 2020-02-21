package de.clayntech.config4j.io;

import java.io.IOException;

public class SourceMissingException extends IOException {
    public SourceMissingException() {
    }

    public SourceMissingException(String message) {
        super(message);
    }

    public SourceMissingException(String message, Throwable cause) {
        super(message, cause);
    }

    public SourceMissingException(Throwable cause) {
        super(cause);
    }
}
