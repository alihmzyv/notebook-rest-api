package com.alihmzyv.notebookrestapi.service;

import com.alihmzyv.notebookrestapi.entity.Note;

import java.util.List;

public interface NoteService {
    List<Note> findAllNotes(int page, int size, List<String> sort);
    List<Note> findAllNotesByUserId(Long id, int page, int size, List<String> sort);
    Note findNoteById(Long noteId);
    void saveNote(Note note);
    Note updateNote(Long noteId, Note note);
    void deleteNoteById(Long noteId);
    void requiresNoteExistsById(Long noteId);
}
