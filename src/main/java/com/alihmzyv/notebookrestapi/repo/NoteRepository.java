package com.alihmzyv.notebookrestapi.repo;

import com.alihmzyv.notebookrestapi.entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {
}
