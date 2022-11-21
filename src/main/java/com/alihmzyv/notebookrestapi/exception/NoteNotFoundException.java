package com.alihmzyv.notebookrestapi.exception;

public class NoteNotFoundException extends NotFoundException {
    public NoteNotFoundException(String message) {
        super(message);
    }
}
