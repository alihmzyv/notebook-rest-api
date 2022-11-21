package com.alihmzyv.notebookrestapi.service.impl;

import com.alihmzyv.notebookrestapi.entity.Note;
import com.alihmzyv.notebookrestapi.entity.User;
import com.alihmzyv.notebookrestapi.exception.UserNotFoundException;
import com.alihmzyv.notebookrestapi.repo.NoteRepository;
import com.alihmzyv.notebookrestapi.repo.UserRepository;
import com.alihmzyv.notebookrestapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepo;
    private NoteRepository noteRepo;

    @Autowired
    public UserServiceImpl(UserRepository userRepo, NoteRepository noteRepo) {
        this.userRepo = userRepo;
        this.noteRepo = noteRepo;
    }

    public User findUserById(Long userId) {
        return userRepo.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(
                        String.format("User could not be found: id = %d", userId)));
    }

    @Override
    public User findUserByEmailAddressOrUsernameAndPassword(String emailAddressOrUsername, String password) {
        return userRepo.findByEmailAddressOrUsernameAndPassword(emailAddressOrUsername, password)
                .orElseThrow(() -> new UserNotFoundException(
                        String.format("User could not be found: emailAddress or username = %s, password = %s",
                                emailAddressOrUsername, password)));
    }

    @Override
    public void saveUser(User user) {
        userRepo.save(user);
    }

    @Override
    public void deleteUserById(Long userId) {
        requiresUserExistsById(userId);
        userRepo.deleteById(userId);
    }

    public List<Note> findNotesByUserId(Long userId) {
        return findUserById(userId).getNotes();
    }

    @Override
    public void createNote(Long userId, Note note) {
        User userFound = findUserById(userId);
        note.setUser(userFound);
        noteRepo.save(note);
    }

    @Override
    public void requiresUserExistsById(Long userId) {
        findUserById(userId);
    }
}
