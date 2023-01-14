package com.alihmzyv.notebookrestapi.entity.model.req.assembler;

import com.alihmzyv.notebookrestapi.entity.Note;
import com.alihmzyv.notebookrestapi.entity.model.req.NoteReqModel;
import org.springframework.stereotype.Service;

@Service
public class NoteAssembler {

    public NoteAssembler() {
    }

    public Note toModel(NoteReqModel entity) {
        return Note.of(entity);
    }
}