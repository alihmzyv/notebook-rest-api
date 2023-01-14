package com.alihmzyv.notebookrestapi.exception.dublicate;

public class UsernameDuplicateNotAllowed extends DuplicateNotAllowedException {
    public UsernameDuplicateNotAllowed(String message) {
        super(message);
    }
}
