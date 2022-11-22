package com.alihmzyv.notebookrestapi.controller;

import com.alihmzyv.notebookrestapi.entity.Note;
import com.alihmzyv.notebookrestapi.entity.User;
import com.alihmzyv.notebookrestapi.entity.model.NoteModel;
import com.alihmzyv.notebookrestapi.entity.model.UserModel;
import com.alihmzyv.notebookrestapi.entity.model.assembler.NoteModelAssembler;
import com.alihmzyv.notebookrestapi.entity.model.assembler.UserModelAssembler;
import com.alihmzyv.notebookrestapi.exception.UserNotFoundException;
import com.alihmzyv.notebookrestapi.repo.NoteRepository;
import com.alihmzyv.notebookrestapi.repo.UserRepository;
import com.alihmzyv.notebookrestapi.service.NoteService;
import com.alihmzyv.notebookrestapi.service.UserService;
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
    public UserController(UserModelAssembler userModelAssembler, NoteModelAssembler noteModelAssembler,
                          UserService userService, NoteService noteService) {
        this.userModelAssembler = userModelAssembler;
        this.noteModelAssembler = noteModelAssembler;
        this.userService = userService;
        this.noteService = noteService;
    }

    @GetMapping
    public CollectionModel<UserModel> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "") List<String> sort) {
        return userModelAssembler.toCollectionModel(userService.findAll(page, size, sort))
                .add(linkTo(methodOn(this.getClass()).findAll(page, size, sort))
                        .withSelfRel());
    }

    @GetMapping(path = "/{userId}")
    public ResponseEntity<UserModel> findUserById(@PathVariable Long userId) {
        return ResponseEntity
                .ok(userModelAssembler.toModel(userService.findUserById(userId)));
    }

    @PostMapping(path = "/search", params = {"emailAddressOrUsername", "password"})
    public ResponseEntity<UserModel> findUserByEmailOrUsernameAndPassword(@RequestParam String emailAddressOrUsername,
                                                                          @RequestParam String password) {
        User userFound = userService.findUserByEmailAddressOrUsernameAndPassword(
                emailAddressOrUsername, password
        );
        return ResponseEntity
                .ok(userModelAssembler.toModel(userFound));
    }

    @PostMapping
    public ResponseEntity<UserModel> createUser(@RequestBody @Valid User user) {
        userService.saveUser(user);
        return ResponseEntity
                .created(userModelAssembler.toModel(user)
                        .getLink("self")
                        .get().toUri())
                .build();
    }

    @PutMapping(path = "/{userId}")
    public ResponseEntity<UserModel> updateUser(
            @PathVariable Long userId,
            @RequestBody @Valid User user) {
        userService.requiresUserExistsById(userId);
        user.setId(userId);
        userService.saveUser(user);
        return ResponseEntity
                .ok(userModelAssembler.toModel(user));
    }

    @DeleteMapping(path = "/{userId}")
    public ResponseEntity<UserModel> deleteUser(@PathVariable Long userId) {
        userService.deleteUserById(userId);
        return ResponseEntity
                .ok(null);
    }

    @GetMapping(path = "/{userId}/notes")
    public ResponseEntity<CollectionModel<NoteModel>> findNotesOfUserByUserId(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "") List<String> sort) {
        List<Note> notes = userService.findNotesByUserId(userId, page, size, sort);
        CollectionModel<NoteModel> collectionModel = noteModelAssembler.toCollectionModel(notes)
                .add(linkTo(methodOn(this.getClass()).findNotesOfUserByUserId(userId, page, size, sort))
                        .withSelfRel());
        return ResponseEntity
                .ok(collectionModel);
    }

    @PostMapping(path = "/{userId}/notes")
    public ResponseEntity<NoteModel> createNote(
            @PathVariable Long userId,
            @RequestBody @Valid Note note) {
        userService.createNote(userId, note);
        return ResponseEntity
                .created(noteModelAssembler.toModel(note)
                        .getLink("self")
                        .get().toUri())
                .build();
    }
}
