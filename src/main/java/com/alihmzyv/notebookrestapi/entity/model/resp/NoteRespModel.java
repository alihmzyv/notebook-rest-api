package com.alihmzyv.notebookrestapi.entity.model.resp;

import com.alihmzyv.notebookrestapi.entity.Note;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;

public class NoteRespModel extends RepresentationModel<NoteRespModel> {
    private String title;
    private String text;
    private LocalDateTime dateTimeCreated;
    private LocalDateTime dateTimeLastModified;

    public NoteRespModel(Note note) {
        this.title = note.getTitle();
        this.text = note.getText();
        this.dateTimeCreated = note.getDateTimeCreated();
        this.dateTimeLastModified = note.getDateTimeLastModified();
    }

    public NoteRespModel() {
    }

    public static NoteRespModel of(Note note) {
        return new NoteRespModel(note);
    }

    public String getText() {
        return text;
    }

    public String getTitle() {
        return title;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getDateTimeCreated() {
        return dateTimeCreated;
    }

    public void setDateTimeCreated(LocalDateTime dateTimeCreated) {
        this.dateTimeCreated = dateTimeCreated;
    }

    public LocalDateTime getDateTimeLastModified() {
        return dateTimeLastModified;
    }

    public void setDateTimeLastModified(LocalDateTime dateTimeLastModified) {
        this.dateTimeLastModified = dateTimeLastModified;
    }
}
