package com.jpa.exercisejpa.exception;

public class FieldRequiredException extends RuntimeException {

    public FieldRequiredException(String text) {
        super(text);
    }

}
