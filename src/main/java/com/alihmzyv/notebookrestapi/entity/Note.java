package com.alihmzyv.notebookrestapi.entity;

import com.alihmzyv.notebookrestapi.entity.model.req.NoteReqModel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "note")
@Getter
@Setter
@RequiredArgsConstructor
@ToString
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "text")
    private String text;

    @Column(name = "created")
    private LocalDateTime dateTimeCreated;

    @Column(name = "last_modified")
    private LocalDateTime dateTimeLastModified;

    @ManyToOne(
            cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.DETACH},
            fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    public static Note of(NoteReqModel entity) {
        Note note = new Note();
        note.setText(entity.getText());
        note.setTitle(entity.getTitle());
        return note;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Note note = (Note) o;
        return Objects.equals(id, note.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
