package com.oop.api.util;

/**
 * The ExceptionMessageFormatter class provides utility methods for formatting exception messages.
 */
public class ExceptionMessageFormatter {
    /**
     * Retrieves the simple data integrity exception message from the given Throwable object.
     * If the Throwable object has a cause, it traverses the cause chain until it reaches the root cause.
     * The localized message of the root cause is returned.
     *
     * @param t the Throwable object from which to retrieve the exception message
     * @return the simple data integrity exception message
     */
    public static String getSimpleDataIntegrityExceptionMessage(Throwable t) {
        while (t.getCause() != null) {
            t = t.getCause();
        }

        return t.getLocalizedMessage();
    }
}
