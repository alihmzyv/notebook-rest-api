package com.alihmzyv.notebookrestapi.entity;

import com.alihmzyv.notebookrestapi.entity.model.req.NoteReqModel;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "note")
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getTitle() {
        return title;
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

    @Override
    public String toString() {
        return "Note{" +
                "id=" + id +
                ", text='" + text + '\'' +
                '}';
    }
}
