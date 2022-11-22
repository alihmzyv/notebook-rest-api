package com.alihmzyv.notebookrestapi.service;

import com.alihmzyv.notebookrestapi.entity.User;

import java.util.List;

public interface UserService {
    List<User> findAllUsers(int page, int size, List<String> sort);
    User findUserById(Long userId);
    User findUserByEmailAddressOrUsernameAndPassword(String emailAddressOrUsername, String password);
    void saveUser(User user);
    void updateUser(Long userId, User user);
    void deleteUserById(Long userId);
    void requiresUserExistsById(Long userId);
}
