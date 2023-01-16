package com.alihmzyv.notebookrestapi.entity.model.resp;

import com.alihmzyv.notebookrestapi.entity.Note;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;

@Getter
@Setter
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

    public static NoteRespModel of(Note note) {
        return new NoteRespModel(note);
    }
}
