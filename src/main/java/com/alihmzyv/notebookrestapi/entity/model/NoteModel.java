package com.alihmzyv.notebookrestapi.entity.model;

import com.alihmzyv.notebookrestapi.entity.Note;
import org.springframework.hateoas.RepresentationModel;

public class NoteModel extends RepresentationModel<NoteModel> {
    private String title;
    private String text;

    public NoteModel(Note note) {
        this.title = note.getTitle();
        this.text = note.getText();
    }

    public NoteModel() {
    }

    public static NoteModel of(Note note) {
        return new NoteModel(note);
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
}
