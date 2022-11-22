package com.alihmzyv.notebookrestapi.service.impl;

import com.alihmzyv.notebookrestapi.entity.Note;
import com.alihmzyv.notebookrestapi.entity.User;
import com.alihmzyv.notebookrestapi.exception.UserNotFoundException;
import com.alihmzyv.notebookrestapi.repo.NoteRepository;
import com.alihmzyv.notebookrestapi.repo.UserRepository;
import com.alihmzyv.notebookrestapi.service.SortingHelper;
import com.alihmzyv.notebookrestapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepo;
    private final NoteRepository noteRepo;
    private final SortingHelper sortingHelper;

    @Autowired
    public UserServiceImpl(UserRepository userRepo, NoteRepository noteRepo, SortingHelper sortingHelper) {
        this.userRepo = userRepo;
        this.noteRepo = noteRepo;
        this.sortingHelper = sortingHelper;
    }

    @Override
    public List<User> findAllUsers(int page, int size, List<String> sort) {
        List<Sort.Order> sortProps = sortingHelper.createSortOrder(sort);
        PageRequest pageable = PageRequest.of(page, size, Sort.by(sortProps));
        return userRepo.findAll(pageable).getContent();
    }

    @Override
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
    public void updateUser(Long userId, User user) {
        requiresUserExistsById(userId);
        user.setId(userId);
        userRepo.save(user);
    }

    @Override
    public void deleteUserById(Long userId) {
        requiresUserExistsById(userId);
        userRepo.deleteById(userId);
    }

    @Override
    public void requiresUserExistsById(Long userId) {
        findUserById(userId);
    }
}
