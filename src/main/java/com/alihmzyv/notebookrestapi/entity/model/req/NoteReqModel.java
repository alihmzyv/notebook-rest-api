package com.alihmzyv.notebookrestapi.entity.model.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@RequiredArgsConstructor
public class NoteReqModel {

    @ApiModelProperty(
            value = "Title of the note."
    )
    private String title;

    @NotBlank(message = "Note cannot be null.")
    @ApiModelProperty(
            value = "Content of the note.",
            required = true
    )
    private String text;

    @Override
    public String toString() {
        return "NoteReqModel{" +
                "title='" + title + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}