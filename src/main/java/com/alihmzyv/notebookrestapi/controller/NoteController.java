package com.alihmzyv.notebookrestapi.controller;

import com.alihmzyv.notebookrestapi.entity.Note;
import com.alihmzyv.notebookrestapi.entity.model.NoteModel;
import com.alihmzyv.notebookrestapi.entity.model.assembler.NoteModelAssembler;
import com.alihmzyv.notebookrestapi.exception.NoteNotFoundException;
import com.alihmzyv.notebookrestapi.repo.NoteRepository;
import com.alihmzyv.notebookrestapi.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/notes")
public class NoteController {
    private NoteService noteService;
    private NoteModelAssembler noteModelAssembler;

    @Autowired
    public NoteController(NoteService noteService, NoteModelAssembler noteModelAssembler) {
        this.noteService = noteService;
        this.noteModelAssembler = noteModelAssembler;
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
