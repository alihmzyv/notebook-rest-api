package com.alihmzyv.notebookrestapi.entity.model.req;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;

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


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "NoteReqModel{" +
                "title='" + title + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}