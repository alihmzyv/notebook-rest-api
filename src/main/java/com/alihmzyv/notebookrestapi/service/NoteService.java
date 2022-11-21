package com.alihmzyv.notebookrestapi.service;

import com.alihmzyv.notebookrestapi.entity.Note;

public interface NoteService {
    Note findNoteById(Long noteId);
    Note updateNote(Long noteId, Note note);
    void deleteNoteById(Long noteId);
    void requiresNoteExistsById(Long noteId);
}
