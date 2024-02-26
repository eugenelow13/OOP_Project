package com.oop.api.util;

public class ExceptionMessageFormatter {
    public static String getSimpleDataIntegrityExceptionMessage(Throwable t) {
        while (t.getCause() != null) {
            t = t.getCause();
        }

        return t.getLocalizedMessage();
    }
}
