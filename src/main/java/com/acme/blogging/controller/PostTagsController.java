package com.acme.blogging.controller;

import com.acme.blogging.domain.model.Post;
import com.acme.blogging.domain.service.PostService;
import com.acme.blogging.resource.PostResource;
import com.acme.blogging.resource.SavePostResource;
import io.swagger.v3.oas.annotations.Operation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
public class PostTagsController {
    @Autowired
    private ModelMapper mapper;

    @Autowired
    private PostService postService;

    @Operation(summary = "Assign Tag to Post", description = "Establishes association between Post and Tag", tags = {"posts"})
    @PostMapping("/posts/{postId}/tags/{tagId}")
    public PostResource assignPostTag(
            @PathVariable(name = "postId") Long postId,
            @PathVariable(name = "tagId") Long tagId) {
        return convertToResource(postService.assignPostTag(postId, tagId));
    }

    @Operation(summary = "Remove assignment between Tag to Post", description = "Ends association between Post and Tag", tags = {"posts"})
    @DeleteMapping("/posts/{postId}/tags/{tagId}")
    public PostResource unassignPostTag(
            @PathVariable(name = "postId") Long postId,
            @PathVariable(name = "tagId") Long tagId) {
        return convertToResource(postService.unassignPostTag(postId, tagId));
    }

    private Post convertToEntity(SavePostResource resource) {
        return mapper.map(resource, Post.class);
    }

    private PostResource convertToResource(Post entity) {
        return mapper.map(entity, PostResource.class);
    }

}
