package com.alihmzyv.notebookrestapi.service.impl;

import com.alihmzyv.notebookrestapi.entity.Note;
import com.alihmzyv.notebookrestapi.exception.NoteNotFoundException;
import com.alihmzyv.notebookrestapi.repo.NoteRepository;
import com.alihmzyv.notebookrestapi.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NoteServiceImpl implements NoteService {
    private NoteRepository noteRepo;

    @Autowired
    public NoteServiceImpl(NoteRepository noteRepo) {
        this.noteRepo = noteRepo;
    }

    @Override
    public Note findNoteById(Long noteId) {
        return noteRepo.findById(noteId)
                .orElseThrow(() ->
                        new NoteNotFoundException(String.format("Note could not be found: id = %d", noteId)));
    }

    @Override
    public Note updateNote(Long noteId, Note note) {
        Note noteFound = findNoteById(noteId);
        noteFound.setText(note.getText());
        noteRepo.save(noteFound);
        return noteFound;
    }

    @Override
    public void deleteNoteById(Long noteId) {
        requiresNoteExistsById(noteId);
        noteRepo.deleteById(noteId);
    }

    @Override
    public void requiresNoteExistsById(Long noteId) {
        findNoteById(noteId);
    }
}
