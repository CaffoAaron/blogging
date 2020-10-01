package com.acme.blogging.controller;

import com.acme.blogging.domain.model.Tag;
import com.acme.blogging.domain.service.TagService;
import com.acme.blogging.resource.SaveTagResource;
import com.acme.blogging.resource.TagResource;
import io.swagger.v3.oas.annotations.Operation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api")
public class TagController {
    @Autowired
    private ModelMapper mapper;

    @Autowired
    private TagService tagService;

    @Operation(summary = "Get Tags By Post", description = "Get Tags associated to given Post", tags = {"posts"})
    @GetMapping("/posts/{postId}/tags")
    public Page<TagResource> getAllTagsByPostId(
            @PathVariable(name = "postId") Long postId,
            Pageable pageable) {
        List<TagResource> tags = tagService.getAllTagsByPostId(postId, pageable)
                .getContent().stream().map(this::convertToResource)
                .collect(Collectors.toList());
        int tagCount = tags.size();
        return new PageImpl<>(tags, pageable, tagCount);
    }


    @Operation(summary = "Get All Tags", description = "Get All available Tags", tags = {"tags"})
    @GetMapping("/tags")
    public Page<TagResource> getAllTags(Pageable pageable) {
        List<TagResource> tags = tagService.getAllTags(pageable)
                .getContent().stream().map(this::convertToResource)
                .collect(Collectors.toList());
        int tagCount = tags.size();
        return new PageImpl<>(tags, pageable, tagCount);

    }

    @Operation(summary = "Get Tag By Id", description = "Get Tag for given Id", tags = {"tags"})
    @GetMapping("/tags/{id}")
    public TagResource getTagById(
            @PathVariable(name = "id") Long tagId) {
        return convertToResource(tagService.getTagById(tagId));
    }

    @Operation(summary = "Create Tag", description = "Create a new Tag", tags = {"tags"})
    @PostMapping("/tags")
    public TagResource createTag(@Valid @RequestBody SaveTagResource resource) {
        return convertToResource(tagService.createTag(
                convertToEntity(resource)));
    }

    @Operation(summary = "Update Tag", description = "Update Tag with given Id", tags = {"tags"})
    @PutMapping("/tags/{id}")
    public TagResource updateTag(
            @PathVariable(name = "id") Long tagId,
            @Valid @RequestBody SaveTagResource resource) {
        return convertToResource(tagService.updateTag(tagId, convertToEntity(resource)));
    }

    private Tag convertToEntity(SaveTagResource resource) {
        return mapper.map(resource, Tag.class);
    }

    private TagResource convertToResource(Tag entity) {
        return mapper.map(entity, TagResource.class);
    }

}
