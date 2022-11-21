package com.alihmzyv.notebookrestapi.entity.model.assembler;

import com.alihmzyv.notebookrestapi.controller.UserController;
import com.alihmzyv.notebookrestapi.entity.User;
import com.alihmzyv.notebookrestapi.entity.model.UserModel;
import lombok.SneakyThrows;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Service;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class UserModelAssembler extends RepresentationModelAssemblerSupport<User, UserModel> {

    public UserModelAssembler() {
        super(UserController.class, UserModel.class);
    }

    @Override
    public UserModel toModel(User entity) {
        return createModelWithId(entity.getId(), entity);
    }

    @Override
    @SneakyThrows
    protected UserModel createModelWithId(Object id, User entity) {
        UserModel userModel = instantiateModel(entity);
        WebMvcLinkBuilder builder = linkTo(methodOn(UserController.class).findUserById(entity.getId()));
        userModel.add(
                builder.withSelfRel(),
                builder.slash("notes")
                        .withRel("notes")
        );
        return userModel;
    }

    @Override
    protected UserModel instantiateModel(User entity) {
        return UserModel.of(entity);
    }
}
