package com.alihmzyv.notebookrestapi.controller;

import com.alihmzyv.notebookrestapi.entity.model.req.NoteReqModel;
import com.alihmzyv.notebookrestapi.entity.model.req.assembler.NoteAssembler;
import com.alihmzyv.notebookrestapi.entity.model.resp.NoteRespModel;
import com.alihmzyv.notebookrestapi.entity.model.resp.assembler.NoteRespModelAssembler;
import com.alihmzyv.notebookrestapi.service.NoteService;
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
@RequestMapping(path = "/notes")
@RequiredArgsConstructor
public class NoteController {
    private final NoteService noteService;
    private final NoteRespModelAssembler noteRespModelAssembler;
    private final NoteAssembler noteAssembler;


    @ApiOperation(
            value = "Retrieve all the notes",
            notes = "Retrieves all the notes.")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Successfully retrieved.")})
    @GetMapping
    public ResponseEntity<CollectionModel<NoteRespModel>> findAllNotes(
            @ApiParam(name = "page number", type = "integer", defaultValue = "0")
            @RequestParam(defaultValue = "0") int page,
            @ApiParam(name = "page size", type = "integer", defaultValue = "10")
            @RequestParam(defaultValue = "10") int size,
            @ApiParam(
                    name = "sort property,order",
                    type = "string",
                    value = "Sorting property and order. The parameter can have multiple values",
                    example = "text,desc")
            @RequestParam(defaultValue = "") List<String> sort) {
        return ResponseEntity
                .ok(noteRespModelAssembler
                        .toCollectionModel(noteService.findAllNotes(page, size, sort))
                        .add(linkTo(methodOn(this.getClass()).findAllNotes(page, size, sort)).withSelfRel()));
    }

    @ApiOperation(
            value = "Retrieve the note by note ID.",
            notes = "Retrieves the note by note ID. note ID should be an integer.")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "Successfully retrieved."),
                    @ApiResponse(code = 404, message = "Note not found: id = ..")})
    @GetMapping(path = "/{noteId}")
    public ResponseEntity<NoteRespModel> findNoteById(
            @ApiParam(name = "note ID", type = "integer", value = "An integer representing note ID")
            @PathVariable Long noteId) {
        return ResponseEntity
                .ok(noteRespModelAssembler.toModel(noteService.findNoteById(noteId)));
    }

    @ApiOperation(
            value = "Update the note by note ID.",
            notes = "Updates the note by note ID. All the required fields should be provided.")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "Successfully updated."),
                    @ApiResponse(code = 404, message = "Note not found: id = ..")})
    @PutMapping(path = "/{noteId}")
    public ResponseEntity<NoteRespModel> updateNote(
            @ApiParam(name = "note ID", type = "integer", value = "An integer representing note ID")
            @PathVariable Long noteId,
            @RequestBody @Valid NoteReqModel noteReq) {
        return ResponseEntity
                .ok(noteRespModelAssembler
                        .toModel(noteService.updateNote(noteId, noteAssembler.toModel(noteReq))));
    }

    @ApiOperation(
            value = "Delete note by note ID.",
            notes = "Deletes the note by note ID.")
    @ApiResponses(
            value = {
                    @ApiResponse(code = 200, message = "Successfully deleted."),
                    @ApiResponse(code = 404, message = "Note not found: id = ..")})
    @DeleteMapping(path = "/{noteId}")
    public ResponseEntity<NoteRespModel> deleteNote(
            @ApiParam(name = "note ID", type = "integer", value = "An integer representing note ID")
            @PathVariable Long noteId) {
        noteService.deleteNoteById(noteId);
        return ResponseEntity.ok().build();
    }
}
