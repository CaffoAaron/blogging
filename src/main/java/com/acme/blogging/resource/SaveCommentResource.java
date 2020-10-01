package com.acme.blogging.resource;

import javax.persistence.Lob;
import javax.validation.constraints.NotNull;

public class SaveCommentResource {
    @NotNull
    @Lob
    private String text;

    public String getText() {
        return text;
    }

    public SaveCommentResource setText(String text) {
        this.text = text;
        return this;
    }
}
