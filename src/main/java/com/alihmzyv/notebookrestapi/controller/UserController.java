package com.alihmzyv.notebookrestapi.controller;

import com.alihmzyv.notebookrestapi.entity.Note;
import com.alihmzyv.notebookrestapi.entity.User;
import com.alihmzyv.notebookrestapi.entity.model.NoteModel;
import com.alihmzyv.notebookrestapi.entity.model.UserModel;
import com.alihmzyv.notebookrestapi.entity.model.assembler.NoteModelAssembler;
import com.alihmzyv.notebookrestapi.entity.model.assembler.UserModelAssembler;
import com.alihmzyv.notebookrestapi.service.NoteService;
import com.alihmzyv.notebookrestapi.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(path = "/users")
public class UserController {
    private final UserModelAssembler userModelAssembler;
    private final NoteModelAssembler noteModelAssembler;
    private final UserService userService;
    private final NoteService noteService;

    @Autowired
    public UserController(UserModelAssembler userModelAssembler,
                          NoteModelAssembler noteModelAssembler,
                          UserService userService,
                          NoteService noteService) {
        this.userModelAssembler = userModelAssembler;
        this.noteModelAssembler = noteModelAssembler;
        this.userService = userService;
        this.noteService = noteService;
    }

    @ApiOperation(
            value = "Retrieve all the users",
            notes = "Retrieves all the users.")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Successfully retrieved.")})
    @GetMapping
    public CollectionModel<UserModel> findAllUsers(
            @ApiParam(name = "page number", defaultValue = "0") @RequestParam(defaultValue = "0") int page,
            @ApiParam(name = "page size", defaultValue = "10") @RequestParam(defaultValue = "10") int size,
            @ApiParam(
                    name = "sort property,order",
                    value = "Sorting property and order. The parameter can have multiple values.",
                    defaultValue = "",
                    example = "firstName,desc") @RequestParam(defaultValue = "") List<String> sort) {
        return userModelAssembler.toCollectionModel(userService.findAllUsers(page, size, sort))
                .add(linkTo(methodOn(this.getClass()).findAllUsers(page, size, sort))
                        .withSelfRel());
    }

    @ApiOperation(
            value = "Create a new user",
            notes = "Creates a new user. Some User properties are subject to validation. Check \"Model\"")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 201, message = "Successfully created."),
                    @ApiResponse(code = 400, message = "Validation error message of the property of User.")})
    @PostMapping
    public ResponseEntity<Object> createUser(@RequestBody @Valid User user) {
        userService.saveUser(user);
        return ResponseEntity
                .created(userModelAssembler.toModel(user)
                        .getLink("self")
                        .get().toUri())
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
    public ResponseEntity<UserModel> findUserById(
            @ApiParam(
                    name = "user ID",
                    value = "An integer representing user ID") @PathVariable Long userId) {
        return ResponseEntity
                .ok(userModelAssembler.toModel(userService.findUserById(userId)));
    }

    @ApiOperation(
            value = "Update the user by user ID.",
            notes = "Updates the user by user ID. All the required fields should be provided.")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "Successfully updated."),
                    @ApiResponse(code = 404, message = "User not found: id = ..")})
    @PutMapping(path = "/{userId}")
    public ResponseEntity<UserModel> updateUser(
            @ApiParam(
                    name = "user ID",
                    value = "An integer representing user ID") @PathVariable Long userId,
                                                              @RequestBody @Valid User user) {
        userService.updateUser(userId, user);
        return ResponseEntity
                .ok(userModelAssembler.toModel(user));
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
            @ApiParam(
                    name = "user ID",
                    value = "An integer representing user ID") @PathVariable Long userId) {
        userService.deleteUserById(userId);
        return ResponseEntity
                .ok()
                .build();
    }

    @ApiOperation(
            value = "Retrieve the user by email address or username and password",
            notes = "Retrieves the user by email address or username and password. Both username and email" +
                    "address can be used.")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "Successfully retrieved."),
                    @ApiResponse(code = 404, message = "User not found: " +
                            "emailAddressOrPassword = .., password = ..")})
    @PostMapping(path = "/search", params = {"emailAddressOrUsername", "password"})
    public ResponseEntity<UserModel> findUserByEmailOrUsernameAndPassword(
            @ApiParam(
                    name = "emailAddressOrUsername",
                    value = "Email address or username of the user.",
                    example = "alihmzyv@gmail.com, or alihmzyv") @RequestParam String emailAddressOrUsername,
                                                                 @RequestParam String password) {
        User userFound = userService.findUserByEmailAddressOrUsernameAndPassword(
                emailAddressOrUsername,
                password);
        return ResponseEntity
                .ok(userModelAssembler.toModel(userFound));
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
    public ResponseEntity<CollectionModel<NoteModel>> findNotesByUserId(
            @ApiParam(
                    name = "user ID",
                    value = "An integer representing user ID") @PathVariable Long userId,
            @ApiParam(name = "page number", defaultValue = "0") @RequestParam(defaultValue = "0") int page,
            @ApiParam(name = "page size", defaultValue = "10") @RequestParam(defaultValue = "10") int size,
            @ApiParam(
                    name = "sort property,order",
                    value = "Sorting property and order. Can have multiple values.",
                    defaultValue = "",
                    example = "text,asc") @RequestParam(defaultValue = "") List<String> sort) {
        userService.requiresUserExistsById(userId);
        List<Note> notes =noteService.findAllNotesByUserId(userId, page, size, sort);
        CollectionModel<NoteModel> collectionModel = noteModelAssembler.toCollectionModel(notes)
                .add(linkTo(methodOn(this.getClass()).findNotesByUserId(userId, page, size, sort))
                        .withSelfRel());
        return ResponseEntity
                .ok(collectionModel);
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
            @ApiParam(
                    name = "user ID",
                    value = "An integer representing user ID") @PathVariable Long userId,
            @RequestBody @Valid Note note) {
        note.setUser(userService.findUserById(userId));
        noteService.saveNote(note);
        return ResponseEntity
                .created(noteModelAssembler.toModel(note)
                        .getLink("self")
                        .get().toUri())
                .build();
    }
}
