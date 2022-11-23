package com.alihmzyv.notebookrestapi.controller;

import com.alihmzyv.notebookrestapi.entity.Note;
import com.alihmzyv.notebookrestapi.entity.model.NoteModel;
import com.alihmzyv.notebookrestapi.entity.model.assembler.NoteModelAssembler;
import com.alihmzyv.notebookrestapi.service.NoteService;
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
@RequestMapping(path = "/notes")
public class NoteController {
    private final NoteService noteService;
    private final NoteModelAssembler noteModelAssembler;

    @Autowired
    public NoteController(NoteService noteService,
                          NoteModelAssembler noteModelAssembler) {
        this.noteService = noteService;
        this.noteModelAssembler = noteModelAssembler;
    }

    @ApiOperation(
            value = "Retrieve all the notes",
            notes = "Retrieves all the notes.")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Successfully retrieved.")})
    @GetMapping
    public CollectionModel<NoteModel> findAllNotes(
            @ApiParam(name = "page number", defaultValue = "0")
            @RequestParam(defaultValue = "0") int page,
            @ApiParam(name = "page size", defaultValue = "10")
            @RequestParam(defaultValue = "10") int size,
            @ApiParam(
                    name = "sort property,order",
                    value = "Sorting property and order. The parameter can have multiple values",
                    example = "text,desc")
            @RequestParam(defaultValue = "") List<String> sort) {
        return noteModelAssembler.toCollectionModel(noteService.findAllNotes(page, size, sort))
                .add(linkTo(methodOn(this.getClass()).findAllNotes(page, size, sort))
                        .withSelfRel());
    }

    @ApiOperation(
            value = "Retrieve the note by note ID.",
            notes = "Retrieves the note by note ID. note ID should be an integer.")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "Successfully retrieved."),
                    @ApiResponse(code = 404, message = "Note not found: id = ..")})
    @GetMapping(path = "/{noteId}")
    public ResponseEntity<NoteModel> findNoteById(
            @ApiParam(name = "note ID", value = "An integer representing note ID")
            @PathVariable Long noteId) {
        return ResponseEntity
                .ok(noteModelAssembler.toModel(noteService.findNoteById(noteId)));
    }

    @ApiOperation(
            value = "Update the note by note ID.",
            notes = "Updates the note by note ID. All the required fields should be provided.")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "Successfully updated."),
                    @ApiResponse(code = 404, message = "Note not found: id = ..")})
    @PutMapping(path = "/{noteId}")
    public ResponseEntity<NoteModel> updateNote(
            @ApiParam(name = "note ID", value = "An integer representing note ID")
            @PathVariable Long noteId,
            @RequestBody @Valid Note note) {
        return ResponseEntity
                .ok(noteModelAssembler.toModel(noteService.updateNote(noteId, note)));
    }

    @ApiOperation(
            value = "Delete note by note ID.",
            notes = "Deletes the note by note ID.")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "Successfully deleted."),
                    @ApiResponse(code = 404, message = "Note not found: id = ..")})
    @DeleteMapping(path = "/{noteId}")
    public ResponseEntity<NoteModel> deleteNote(
            @ApiParam(name = "note ID", value = "An integer representing note ID")
            @PathVariable Long noteId) {
        noteService.deleteNoteById(noteId);
        return ResponseEntity
                .ok()
                .build();
    }
}
