/*ackage org.grostarin.springboot.demorest.exceptions;

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

 */

package org.grostarin.springboot.demorest.exceptions;

public class BannedBookException extends RuntimeException {

    public BannedBookException() {
        super("Livre interdit");
    }

    public BannedBookException(final String message, final Throwable cause) {
        super("Livre interdit: " + message, cause);
    }

    public BannedBookException(final String message) {
        super("Livre interdit: " + message);
    }

    public BannedBookException(final Throwable cause) {
        super("Livre interdit", cause);
    }
}
