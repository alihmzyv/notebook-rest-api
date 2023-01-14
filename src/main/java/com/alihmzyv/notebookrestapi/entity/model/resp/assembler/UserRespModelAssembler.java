package com.alihmzyv.notebookrestapi.entity.model.resp.assembler;

import com.alihmzyv.notebookrestapi.controller.UserController;
import com.alihmzyv.notebookrestapi.entity.User;
import com.alihmzyv.notebookrestapi.entity.model.resp.UserRespModel;
import lombok.SneakyThrows;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Service;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class UserRespModelAssembler extends RepresentationModelAssemblerSupport<User, UserRespModel> {

    public UserRespModelAssembler() {
        super(UserController.class, UserRespModel.class);
    }

    @Override
    public UserRespModel toModel(User entity) {
        return createModelWithId(entity.getId(), entity);
    }

    @Override
    @SneakyThrows
    protected UserRespModel createModelWithId(Object id, User entity) {
        UserRespModel userRespModel = instantiateModel(entity);
        WebMvcLinkBuilder linkBuilder = linkTo(methodOn(UserController.class).findUserById(entity.getId()));
        userRespModel.add(
                linkBuilder.withSelfRel(),
                linkBuilder.slash("notes")
                        .withRel("notes")
        );
        return userRespModel;
    }

    @Override
    protected UserRespModel instantiateModel(User entity) {
        return UserRespModel.of(entity);
    }
}
