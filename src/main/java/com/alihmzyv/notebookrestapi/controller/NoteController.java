package com.alihmzyv.notebookrestapi.controller;

import com.alihmzyv.notebookrestapi.entity.Note;
import com.alihmzyv.notebookrestapi.entity.model.NoteModel;
import com.alihmzyv.notebookrestapi.entity.model.assembler.NoteModelAssembler;
import com.alihmzyv.notebookrestapi.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(path = "/notes")
public class NoteController {
    private final NoteService noteService;
    private final NoteModelAssembler noteModelAssembler;

    @Autowired
    public NoteController(NoteService noteService, NoteModelAssembler noteModelAssembler) {
        this.noteService = noteService;
        this.noteModelAssembler = noteModelAssembler;
    }

    @GetMapping
    public CollectionModel<NoteModel> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "") List<String> sort) {
        return noteModelAssembler.toCollectionModel(noteService.findAllNotes(page, size, sort))
                .add(linkTo(methodOn(this.getClass()).findAll(page, size, sort))
                        .withSelfRel());
    }

    @GetMapping(path = "/{noteId}")
    public ResponseEntity<NoteModel> findNoteById(@PathVariable Long noteId) {
        return ResponseEntity
                .ok(noteModelAssembler.toModel(noteService.findNoteById(noteId)));
    }

    @PutMapping(path = "/{noteId}")
    public ResponseEntity<NoteModel> updateNote(
            @PathVariable Long noteId,
            @RequestBody @Valid Note note) {
        return ResponseEntity
                .ok(noteModelAssembler.toModel(noteService.updateNote(noteId, note)));
    }

    @DeleteMapping(path = "/{noteId}")
    public ResponseEntity<NoteModel> deleteNote(
            @PathVariable Long noteId) {
        noteService.deleteNoteById(noteId);
        return ResponseEntity
                .ok().build();
    }
}
