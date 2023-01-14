package com.alihmzyv.notebookrestapi.exception.dublicate;

public class DuplicateNotAllowedException extends RuntimeException {
    public DuplicateNotAllowedException(String message) {
        super(message);
    }
}
