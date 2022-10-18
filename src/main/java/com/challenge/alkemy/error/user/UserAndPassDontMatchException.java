package com.challenge.alkemy.error.user;

public class UserAndPassDontMatchException extends Exception {

    public UserAndPassDontMatchException(String message) {
        super(message);
    }
}

