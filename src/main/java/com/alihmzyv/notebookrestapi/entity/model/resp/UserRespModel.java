package com.alihmzyv.notebookrestapi.entity.model.resp;

import com.alihmzyv.notebookrestapi.entity.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Setter
@RequiredArgsConstructor
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

    public static UserRespModel of(User user) {
        return new UserRespModel(user);
    }
}
