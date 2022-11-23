package com.alihmzyv.notebookrestapi.entity.model;

import com.alihmzyv.notebookrestapi.entity.User;
import org.springframework.hateoas.RepresentationModel;

public class UserModel extends RepresentationModel<UserModel> {
    private String firstName;
    private String lastName;
    private String username;
    private String emailAddress;

    public UserModel(User user) {
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.emailAddress = user.getEmailAddress();
        this.username = user.getUsername();
    }

    public UserModel() {
    }

    public static UserModel of(User user) {
        return new UserModel(user);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }
}
