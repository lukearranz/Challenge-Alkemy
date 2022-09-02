package com.challenge.alkemy.error;

public class PersonajeNotFoundException extends Exception {

    public PersonajeNotFoundException() {
        super();
    }

    public PersonajeNotFoundException(String message) {
        super(message);
    }

    public PersonajeNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public PersonajeNotFoundException(Throwable cause) {
        super(cause);
    }

    protected PersonajeNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
