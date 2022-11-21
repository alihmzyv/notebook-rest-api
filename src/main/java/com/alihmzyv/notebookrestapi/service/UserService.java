package com.alihmzyv.notebookrestapi.service;

import com.alihmzyv.notebookrestapi.entity.Note;
import com.alihmzyv.notebookrestapi.entity.User;

import java.util.List;

public interface UserService {
    User findUserById(Long userId);
    User findUserByEmailAddressOrUsernameAndPassword(String emailAddressOrUsername, String password);
    void saveUser(User user);
    void deleteUserById(Long userId);
    List<Note> findNotesByUserId(Long userId);
    void createNote(Long userId, Note note);
    void requiresUserExistsById(Long userId);
}
