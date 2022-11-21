package com.alihmzyv.notebookrestapi.entity.model.assembler;

import com.alihmzyv.notebookrestapi.controller.NoteController;
import com.alihmzyv.notebookrestapi.entity.Note;
import com.alihmzyv.notebookrestapi.entity.User;
import com.alihmzyv.notebookrestapi.entity.model.NoteModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Service;

@Service
public class NoteModelAssembler extends RepresentationModelAssemblerSupport<Note, NoteModel> {

    private UserModelAssembler userModelAssembler;

    @Autowired
    public NoteModelAssembler(UserModelAssembler userModelAssembler) {
        super(NoteController.class, NoteModel.class);
        this.userModelAssembler = userModelAssembler;
    }

    @Override
    public NoteModel toModel(Note entity) {
        return createModelWithId(entity.getId(), entity)
                .add(userModelAssembler.toModel(entity.getUser()).getRequiredLink("self")
                        .withRel("user"));
    }

    @Override
    protected NoteModel instantiateModel(Note entity) {
        return NoteModel.of(entity);
    }
}
