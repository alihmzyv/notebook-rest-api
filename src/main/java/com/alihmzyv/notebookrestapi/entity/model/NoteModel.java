package com.alihmzyv.notebookrestapi.entity.model;

import com.alihmzyv.notebookrestapi.entity.Note;
import com.alihmzyv.notebookrestapi.entity.User;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;

public class NoteModel extends RepresentationModel<NoteModel> {
    private String text;

    public NoteModel(Note note) {
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

    public void setText(String text) {
        this.text = text;
    }
}
