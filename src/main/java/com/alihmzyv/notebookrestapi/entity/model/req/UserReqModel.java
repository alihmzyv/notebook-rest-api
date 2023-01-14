package com.alihmzyv.notebookrestapi.entity.model.req;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Objects;

public class UserReqModel {
    @NotBlank(message = "First name is a required field.")
    @ApiModelProperty(
            value = "First name of the user.",
            required = true
    )
    private String firstName;

    @NotBlank(message = "Last name is a required field.")
    @ApiModelProperty(
            value = "Last name of the user.",
            required = true
    )
    private String lastName;

    @NotBlank(message = "Username is a required field.")
    @ApiModelProperty(
            value = "Username of the user. Should be unique.",
            required = true
    )
    private String username;

    @NotBlank(message = "Email Address is a required field.")
    @Email(message = "Email address should be a well-formed email address.")
    @ApiModelProperty(
            value = "Email address of the user. Should be a unique, well-formed email address.",
            example = "alihmzyv@gmail.com",
            required = true
    )
    private String emailAddress;

    @NotBlank(message = "Password is a required field.")
    @Size(min = 6, message = "Password should contain at least 6 characters")
    @ApiModelProperty(
            value = "Password of the user. Should contain at least 6 characters.",
            required = true
    )
    private String password;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserReqModel{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", username='" + username + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserReqModel that = (UserReqModel) o;
        return Objects.equals(username, that.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }
}

