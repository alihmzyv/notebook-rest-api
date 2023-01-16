package com.alihmzyv.notebookrestapi.entity.model.req.assembler;

import com.alihmzyv.notebookrestapi.entity.User;
import com.alihmzyv.notebookrestapi.entity.model.req.UserReqModel;
import org.springframework.stereotype.Service;

@Service
public class UserAssembler {
    public User toModel(UserReqModel entity) {
        return User.of(entity);
    }
}
