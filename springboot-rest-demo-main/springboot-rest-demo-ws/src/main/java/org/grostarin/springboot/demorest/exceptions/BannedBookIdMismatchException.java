package org.grostarin.springboot.demorest.exceptions;

public class BannedBookIdMismatchException extends RuntimeException {

    public BannedBookIdMismatchException() {
        super();
    }

    public BannedBookIdMismatchException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public BannedBookIdMismatchException(final String message) {
        super(message);
    }

    public BannedBookIdMismatchException(final Throwable cause) {
        super(cause);
    }
}
