package com.acme.blogging.controller;

import com.acme.blogging.domain.model.Post;
import com.acme.blogging.domain.service.PostService;
import com.acme.blogging.resource.PostResource;
import com.acme.blogging.resource.SavePostResource;
import io.swagger.v3.oas.annotations.Operation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class TagPostsController {
    @Autowired
    private ModelMapper mapper;

    @Operation(summary = "Get Posts by Tag", description = "Get Posts for given Tag", tags = {"tags"})
    @GetMapping("/tags/{tagId}/posts")
    public Page<PostResource> getAllPostsByTagId(
            @PathVariable(name = "tagId") Long tagId,
            Pageable pageable) {
        Page<Post> postsPage = postService.getAllPostsByTagId(tagId, pageable);
        List<PostResource> resources = postsPage.getContent().stream()
                .map(this::convertToResource).collect(Collectors.toList());
        return new PageImpl<>(resources, pageable, resources.size());
    }

    @Autowired
    private PostService postService;

    private Post convertToEntity(SavePostResource resource) {
        return mapper.map(resource, Post.class);
    }

    private PostResource convertToResource(Post entity) {
        return mapper.map(entity, PostResource.class);
    }

}
