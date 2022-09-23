package org.example.exceptions;

public class IllegalBeanException extends IllegalArgumentException {
    public IllegalBeanException(String reason) {
        super(reason);
    }
}
