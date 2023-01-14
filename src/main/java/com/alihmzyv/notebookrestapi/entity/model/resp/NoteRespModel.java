package com.alihmzyv.notebookrestapi.entity.model.resp;

import com.alihmzyv.notebookrestapi.entity.Note;
import org.springframework.hateoas.RepresentationModel;

public class NoteRespModel extends RepresentationModel<NoteRespModel> {
    private String title;
    private String text;

    public NoteRespModel(Note note) {
        this.title = note.getTitle();
        this.text = note.getText();
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
}
