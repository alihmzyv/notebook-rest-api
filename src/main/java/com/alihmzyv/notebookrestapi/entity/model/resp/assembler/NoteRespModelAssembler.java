package com.alihmzyv.notebookrestapi.entity.model.resp.assembler;

import com.alihmzyv.notebookrestapi.controller.NoteController;
import com.alihmzyv.notebookrestapi.entity.Note;
import com.alihmzyv.notebookrestapi.entity.model.resp.NoteRespModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Service;

@Service
public class NoteRespModelAssembler extends RepresentationModelAssemblerSupport<Note, NoteRespModel> {

    private final UserRespModelAssembler userRespModelAssembler;

    @Autowired
    public NoteRespModelAssembler(UserRespModelAssembler userRespModelAssembler) {
        super(NoteController.class, NoteRespModel.class);
        this.userRespModelAssembler = userRespModelAssembler;
    }

    @Override
    public NoteRespModel toModel(Note entity) {
        return createModelWithId(entity.getId(), entity)
                .add(userRespModelAssembler.toModel(entity.getUser()).getRequiredLink("self")
                        .withRel("user"));
    }

    @Override
    protected NoteRespModel instantiateModel(Note entity) {
        return NoteRespModel.of(entity);
    }
}
