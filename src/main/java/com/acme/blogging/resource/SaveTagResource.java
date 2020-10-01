package com.acme.blogging.resource;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class SaveTagResource {
    @NotNull
    @Size(max = 100)
    private String name;

    public String getName() {
        return name;
    }

    public SaveTagResource setName(String name) {
        this.name = name;
        return this;
    }
}
