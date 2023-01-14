package com.alihmzyv.notebookrestapi.entity.model.resp;

import com.alihmzyv.notebookrestapi.entity.User;
import org.springframework.hateoas.RepresentationModel;

public class UserRespModel extends RepresentationModel<UserRespModel> {
    private String firstName;
    private String lastName;
    private String username;
    private String emailAddress;

    public UserRespModel(User user) {
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.emailAddress = user.getEmailAddress();
        this.username = user.getUsername();
    }

    public UserRespModel() {
    }

    public static UserRespModel of(User user) {
        return new UserRespModel(user);
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
