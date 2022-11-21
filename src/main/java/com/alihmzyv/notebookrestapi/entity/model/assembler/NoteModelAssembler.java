package com.alihmzyv.notebookrestapi.entity.model.assembler;

import com.alihmzyv.notebookrestapi.controller.NoteController;
import com.alihmzyv.notebookrestapi.entity.Note;
import com.alihmzyv.notebookrestapi.entity.model.NoteModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Service;

@Service
public class NoteModelAssembler extends RepresentationModelAssemblerSupport<Note, NoteModel> {

    public NoteModelAssembler() {
        super(NoteController.class, NoteModel.class);
    }

    @Override
    public NoteModel toModel(Note entity) {
        return createModelWithId(entity.getId(), entity);
    }

    @Override
    protected NoteModel instantiateModel(Note entity) {
        return NoteModel.of(entity);
    }
}
