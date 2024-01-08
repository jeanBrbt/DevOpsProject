package org.grostarin.springboot.demorest.exceptions;

public class BannedBookNotFoundException extends RuntimeException {

    public BannedBookNotFoundException() {
        super();
    }

    public BannedBookNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public BannedBookNotFoundException(final String message) {
        super(message);
    }

    public BannedBookNotFoundException(final Throwable cause) {
        super(cause);
    }

}