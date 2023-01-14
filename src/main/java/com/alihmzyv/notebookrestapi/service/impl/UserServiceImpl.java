package com.alihmzyv.notebookrestapi.service.impl;

import com.alihmzyv.notebookrestapi.entity.User;
import com.alihmzyv.notebookrestapi.exception.dublicate.EmailAddressDuplicateNotAllowed;
import com.alihmzyv.notebookrestapi.exception.dublicate.UsernameDuplicateNotAllowed;
import com.alihmzyv.notebookrestapi.exception.notfound.UserNotFoundException;
import com.alihmzyv.notebookrestapi.repo.NoteRepository;
import com.alihmzyv.notebookrestapi.repo.UserRepository;
import com.alihmzyv.notebookrestapi.service.SortingHelper;
import com.alihmzyv.notebookrestapi.service.UserService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepo;
    private final NoteRepository noteRepo;
    private final SortingHelper sortingHelper;
    private final PasswordEncoder passwordEncoder;


    public UserServiceImpl(UserRepository userRepo,
                           NoteRepository noteRepo,
                           SortingHelper sortingHelper,
                           PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.noteRepo = noteRepo;
        this.sortingHelper = sortingHelper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<User> findAllUsers(int page, int size, List<String> sort) {
        List<Sort.Order> sortProps = sortingHelper.createSortOrder(sort);
        PageRequest pageable = PageRequest.of(page, size, Sort.by(sortProps));
        return userRepo.findAll(pageable).getContent();
    }

    @Override
    public User findUserById(Long userId) {
        return userRepo
                .findById(userId)
                .orElseThrow(() ->
                        new UserNotFoundException(String.format("User could not be found: id = %d", userId)));
    }

    @Override
    public User findUserByEmailAddressOrUsernameAndPassword(String emailAddressOrUsername, String password) {
        Optional<User> userOpt = userRepo
                .findByEmailAddressOrUsername(emailAddressOrUsername);
        if (userOpt.isEmpty() || !passwordEncoder.matches(password, userOpt.get().getPassword())) {
            throw new UserNotFoundException(
                    String.format("User could not be found: emailAddress or username = %s, password = %s",
                            emailAddressOrUsername, password));
        }
        return userOpt.get();
    }

    @Override
    public User saveUser(User user) {
        requiresUniqueUserDetails(user);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepo.save(user);
    }

    @Override
    public User updateUser(Long userId, User user) {
        requiresUserExistsById(userId);
        user.setId(userId);
        return saveUser(user);
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

    public void requiresUniqueUserDetails(User user) {
        if (userRepo.existsByUsername(user.getUsername())) {
            throw new UsernameDuplicateNotAllowed(String.format(
                    "The username is already in use: %s", user.getUsername()));
        }
        if (userRepo.existsByEmailAddress(user.getEmailAddress())) {
            throw new EmailAddressDuplicateNotAllowed(String.format(
                    "The email address is already in use: %s", user.getEmailAddress()));
        }
    }
}
