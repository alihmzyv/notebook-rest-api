package com.alihmzyv.notebookrestapi.entity.model.req.assembler;

import com.alihmzyv.notebookrestapi.entity.Note;
import com.alihmzyv.notebookrestapi.entity.model.req.NoteReqModel;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
public class NoteAssembler {

    public Note toModel(NoteReqModel entity) {
        return Note.of(entity);
    }
}