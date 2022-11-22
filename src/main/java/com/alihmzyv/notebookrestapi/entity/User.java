package com.alihmzyv.notebookrestapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;
import springfox.documentation.annotations.ApiIgnore;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @JsonIgnore
    private Long id;

    @Column(name = "firstName")
    @NotBlank(message = "First name is a required field.")
    @ApiModelProperty(
            value = "First name of the user.",
            required = true
    )
    private String firstName;

    @Column(name = "lastName")
    @NotBlank(message = "Last name is a required field.")
    @ApiModelProperty(
            value = "Last name of the user.",
            required = true
    )
    private String lastName;

    @Column(name = "username")
    @NotBlank(message = "Username is a required field.")
    @ApiModelProperty(
            value = "Username of the user. Should be unique.",
            required = true
    )
    private String username;

    @Column(name = "emailAddress")
    @NotBlank(message = "Email Address is a required field.")
    @Email(message = "Email address should be a well-formed email address.")
    @ApiModelProperty(
            value = "Email address of the user. Should be a unique, well-formed email address.",
            required = true
    )
    private String emailAddress;

    @Column(name = "password")
    @NotBlank(message = "Password is a required field.")
    @Size(min = 6, message = "Password should contain at least 6 characters")
    @ApiModelProperty(
            value = "Password of the user. Should contain at least 6 characters.",
            required = true
    )
    private String password;

    @OneToMany(mappedBy = "user",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Note> notes;

    public User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Note> getNotes() {
        return notes;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }

    public void addNote(Note note) {
        note.setUser(this);
        this.notes.add(note);
    }

    public void removeNote(Note note) {
        this.notes.remove(note);
    }

    public void addNotes(Collection<Note> notes) {
        this.notes.addAll(notes);
    }

    public void removeNotes(Collection<Note> notes) {
        this.notes.removeAll(notes);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
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
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
