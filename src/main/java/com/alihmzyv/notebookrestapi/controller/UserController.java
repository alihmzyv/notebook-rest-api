package com.alihmzyv.notebookrestapi.controller;

import com.alihmzyv.notebookrestapi.entity.Note;
import com.alihmzyv.notebookrestapi.entity.User;
import com.alihmzyv.notebookrestapi.entity.model.req.UserReqModel;
import com.alihmzyv.notebookrestapi.entity.model.req.assembler.UserAssembler;
import com.alihmzyv.notebookrestapi.entity.model.resp.NoteRespModel;
import com.alihmzyv.notebookrestapi.entity.model.resp.UserRespModel;
import com.alihmzyv.notebookrestapi.entity.model.resp.assembler.NoteRespModelAssembler;
import com.alihmzyv.notebookrestapi.entity.model.resp.assembler.UserRespModelAssembler;
import com.alihmzyv.notebookrestapi.service.NoteService;
import com.alihmzyv.notebookrestapi.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(path = "/users")
@RequiredArgsConstructor
public class UserController {
    private final UserRespModelAssembler userRespModelAssembler;
    private final UserAssembler userAssembler;
    private final NoteRespModelAssembler noteRespModelAssembler;
    private final UserService userService;
    private final NoteService noteService;

    @ApiOperation(value = "Retrieve all the users", notes = "Retrieves all the users.")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Successfully retrieved.")})
    @GetMapping
    public ResponseEntity<CollectionModel<UserRespModel>> findAllUsers(
            @ApiParam(name = "page number", type = "integer", defaultValue = "0")
            @RequestParam(defaultValue = "0") int page,
            @ApiParam(name = "page size", type = "integer", defaultValue = "10")
            @RequestParam(defaultValue = "10") int size,
            @ApiParam(name = "sort property,order",
                      type = "string",
                      value = "Sorting property and order. The parameter can have multiple values.",
                      example = "firstName,desc")
            @RequestParam(defaultValue = "") List<String> sort) {
        return ResponseEntity
                .ok(userRespModelAssembler
                        .toCollectionModel(userService.findAllUsers(page, size, sort))
                        .add(linkTo(methodOn(this.getClass()).findAllUsers(page, size, sort)).withSelfRel()));
    }

    @ApiOperation
            (value = "Create a new user",
            notes = "Creates a new user. Some User properties are subject to validation. Check \"Model\"")
    @ApiResponses
            (value = {
                    @ApiResponse(code = 201, message = "Successfully created."),
                    @ApiResponse(code = 400, message = "Validation error message of the property of User.")})
    @PostMapping
    public ResponseEntity<Object> createUser(@RequestBody @Valid UserReqModel userReq) {
        User user = userAssembler.toModel(userReq);
        userService.saveUser(user);
        return ResponseEntity
                .created(userRespModelAssembler.toModel(user)
                        .getLink("self").get()
                        .toUri())
                .build();
    }

    @ApiOperation(
            value = "Retrieve the user by user ID.",
            notes = "Retrieves the user by user ID. user ID should be an integer.")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "Successfully retrieved."),
                    @ApiResponse(code = 404, message = "User not found: id = ..")})
    @GetMapping(path = "/{userId}")
    public ResponseEntity<UserRespModel> findUserById(
            @ApiParam(name = "user ID", type = "integer", value = "An integer representing user ID")
            @PathVariable Long userId) {
        return ResponseEntity
                .ok(userRespModelAssembler.toModel(userService.findUserById(userId)));
    }

    @ApiOperation(
            value = "Update the user by user ID.",
            notes = "Updates the user by user ID. All the required fields should be provided.")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "Successfully updated."),
                    @ApiResponse(code = 404, message = "User not found: id = ..")})
    @PutMapping(path = "/{userId}")
    public ResponseEntity<UserRespModel> updateUser(
            @ApiParam(name = "user ID", type = "integer", value = "An integer representing user ID")
            @PathVariable Long userId,
            @RequestBody @Valid UserReqModel userReq) {
        User user = userAssembler.toModel(userReq);
        userService.updateUser(userId, user);
        return ResponseEntity
                .ok(userRespModelAssembler.toModel(user));
    }

    @ApiOperation(
            value = "Delete user by user ID.",
            notes = "Deletes the user by user ID.")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "Successfully deleted."),
                    @ApiResponse(code = 404, message = "User not found: id = ..")})
    @DeleteMapping(path = "/{userId}")
    public ResponseEntity<Object> deleteUser(
            @ApiParam(name = "user ID", type = "integer", value = "An integer representing user ID")
            @PathVariable Long userId) {
        userService.deleteUserById(userId);
        return ResponseEntity.ok().build();
    }

    @ApiOperation(
            value = "Retrieve the user by email address or username and password",
            notes = "Retrieves the user by email address or username and password. " +
                    "Both username and email address can be used.")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "Successfully retrieved."),
                    @ApiResponse(code = 404, message = "User not found: emailAddressOrPassword = .., password = ..")})
    @PostMapping(path = "/search", params = {"emailAddressOrUsername", "password"})
    public ResponseEntity<UserRespModel> findUserByEmailOrUsernameAndPassword(
            @ApiParam(
                    name = "emailAddressOrUsername",
                    type = "string",
                    value = "Email address or username of the user.",
                    example = "alihmzyv")
            @RequestParam String emailAddressOrUsername,
            @RequestParam String password) {
        User userFound = userService.findUserByEmailAddressOrUsernameAndPassword(
                emailAddressOrUsername,
                password);
        return ResponseEntity
                .ok(userRespModelAssembler.toModel(userFound));
    }

    //notes
    @ApiOperation(
            value = "Retrieve all the notes by the user by user ID.",
            notes = "Retrieves all the notes by the user by user ID.")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "Successfully retrieved."),
                    @ApiResponse(code = 404, message = "User not found: id = ..")})
    @GetMapping(path = "/{userId}/notes")
    public ResponseEntity<CollectionModel<NoteRespModel>> findNotesByUserId(
            @ApiParam(name = "user ID", type = "integer", value = "An integer representing user ID")
            @PathVariable Long userId,
            @ApiParam(name = "page number", type = "integer", defaultValue = "0")
            @RequestParam(defaultValue = "0") int page,
            @ApiParam(name = "page size", type = "integer", defaultValue = "10")
            @RequestParam(defaultValue = "10") int size,
            @ApiParam(
                    name = "sort property,order",
                    type = "string",
                    value = "Sorting property and order. Can have multiple values.",
                    example = "text,asc")
            @RequestParam(defaultValue = "") List<String> sort) {
        userService.requiresUserExistsById(userId);
        List<Note> notes = noteService.findAllNotesByUserId(userId, page, size, sort);
        CollectionModel<NoteRespModel> collectionModel = noteRespModelAssembler
                .toCollectionModel(notes)
                .add(linkTo(methodOn(this.getClass()).findNotesByUserId(userId, page, size, sort)).withSelfRel());
        return ResponseEntity.ok(collectionModel);
    }

    @ApiOperation(
            value = "Creates the note for the user by user ID.",
            notes = "Creates the note for the user by user ID.")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 201, message = "Successfully created."),
                    @ApiResponse(code = 404, message = "User not found: id = ..")})
    @PostMapping(path = "/{userId}/notes")
    public ResponseEntity<Object> createNote(
            @ApiParam(name = "user ID", type = "integer", value = "An integer representing user ID")
            @PathVariable Long userId,
            @RequestBody @Valid Note note) {
        note.setUser(userService.findUserById(userId));
        noteService.saveNote(note);
        return ResponseEntity
                .created(noteRespModelAssembler.toModel(note)
                        .getLink("self")
                        .get().toUri())
                .build();
    }
}
