package com.alihmzyv.notebookrestapi.controller;

import com.alihmzyv.notebookrestapi.entity.Note;
import com.alihmzyv.notebookrestapi.entity.User;
import com.alihmzyv.notebookrestapi.entity.model.NoteModel;
import com.alihmzyv.notebookrestapi.entity.model.UserModel;
import com.alihmzyv.notebookrestapi.entity.model.assembler.NoteModelAssembler;
import com.alihmzyv.notebookrestapi.entity.model.assembler.UserModelAssembler;
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
    public UserController(UserModelAssembler userModelAssembler,
                          NoteModelAssembler noteModelAssembler,
                          UserService userService,
                          NoteService noteService) {
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
        return userModelAssembler.toCollectionModel(userService.findAllUsers(page, size, sort))
                .add(linkTo(methodOn(this.getClass()).findAll(page, size, sort))
                        .withSelfRel());
    }

    @PostMapping
    public ResponseEntity<Object> createUser(@RequestBody @Valid User user) {
        userService.saveUser(user);
        return ResponseEntity
                .created(userModelAssembler.toModel(user)
                        .getLink("self")
                        .get().toUri())
                .build();
    }

    @GetMapping(path = "/{userId}")
    public ResponseEntity<UserModel> findUserById(@PathVariable Long userId) {
        return ResponseEntity
                .ok(userModelAssembler.toModel(userService.findUserById(userId)));
    }

    @PutMapping(path = "/{userId}")
    public ResponseEntity<UserModel> updateUser(@PathVariable Long userId, @RequestBody @Valid User user) {
        userService.updateUser(userId, user);
        return ResponseEntity
                .ok(userModelAssembler.toModel(user));
    }

    @DeleteMapping(path = "/{userId}")
    public ResponseEntity<Object> deleteUser(@PathVariable Long userId) {
        userService.deleteUserById(userId);
        return ResponseEntity
                .ok()
                .build();
    }

    @PostMapping(path = "/search", params = {"emailAddressOrUsername", "password"})
    public ResponseEntity<UserModel> findUserByEmailOrUsernameAndPassword(@RequestParam String emailAddressOrUsername,
                                                                          @RequestParam String password) {
        User userFound = userService.findUserByEmailAddressOrUsernameAndPassword(
                emailAddressOrUsername,
                password);
        return ResponseEntity
                .ok(userModelAssembler.toModel(userFound));
    }

    //notes
    @GetMapping(path = "/{userId}/notes")
    public ResponseEntity<CollectionModel<NoteModel>> findNotesByUserId(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "") List<String> sort) {
        userService.requiresUserExistsById(userId);
        List<Note> notes =noteService.findAllNotesByUserId(userId, page, size, sort);
        CollectionModel<NoteModel> collectionModel = noteModelAssembler.toCollectionModel(notes)
                .add(linkTo(methodOn(this.getClass()).findNotesByUserId(userId, page, size, sort))
                        .withSelfRel());
        return ResponseEntity
                .ok(collectionModel);
    }

    @PostMapping(path = "/{userId}/notes")
    public ResponseEntity<Object> createNote(
            @PathVariable Long userId,
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
