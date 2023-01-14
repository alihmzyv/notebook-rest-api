package com.alihmzyv.notebookrestapi.service.impl;

import com.alihmzyv.notebookrestapi.entity.Note;
import com.alihmzyv.notebookrestapi.exception.notfound.NoteNotFoundException;
import com.alihmzyv.notebookrestapi.repo.NoteRepository;
import com.alihmzyv.notebookrestapi.service.NoteService;
import com.alihmzyv.notebookrestapi.service.SortingHelper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NoteServiceImpl implements NoteService {
    private final NoteRepository noteRepo;
    private final SortingHelper sortingHelper;


    public NoteServiceImpl(NoteRepository noteRepo, SortingHelper sortingHelper) {
        this.noteRepo = noteRepo;
        this.sortingHelper = sortingHelper;
    }

    @Override
    public List<Note> findAllNotes(int page, int size, List<String> sort) {
        List<Sort.Order> sortProps = sortingHelper.createSortOrder(sort);
        PageRequest pageable = PageRequest.of(page, size, Sort.by(sortProps));
        return noteRepo.findAll(pageable).getContent();
    }

    @Override
    public List<Note> findAllNotesByUserId(Long userId, int page, int size, List<String> sort) {
        List<Sort.Order> sortProps = sortingHelper.createSortOrder(sort);
        PageRequest pageable = PageRequest.of(page, size, Sort.by(sortProps));
        return noteRepo.findAllByUserId(userId, pageable);
    }

    @Override
    public Note findNoteById(Long noteId) {
        return noteRepo
                .findById(noteId)
                .orElseThrow(() ->
                        new NoteNotFoundException(String.format("Note could not be found: id = %d", noteId)));
    }

    @Override
    public Note saveNote(Note note) {
        LocalDateTime now = LocalDateTime.now();
        note.setDateTimeCreated(now);
        note.setDateTimeLastModified(now);
        return noteRepo.save(note);
    }

    @Override
    public Note updateNote(Long noteId, Note note) {
        Note noteFound = findNoteById(noteId);
        note.setId(noteId);
        note.setUser(noteFound.getUser());
        note.setDateTimeLastModified(LocalDateTime.now());
        return noteRepo.save(note);
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
