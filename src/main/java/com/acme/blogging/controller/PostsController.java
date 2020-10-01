package com.acme.blogging.controller;

import com.acme.blogging.domain.model.Post;
import com.acme.blogging.domain.service.PostService;
import com.acme.blogging.resource.PostResource;
import com.acme.blogging.resource.SavePostResource;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api")
public class PostsController {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private PostService postService;

    @Operation(summary = "Get Posts", description = "Get All Posts by Pages", tags = {"posts"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All Posts returned", content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/posts")
    public Page<PostResource> getAllPosts(Pageable pageable) {
        Page<Post> postsPage = postService.getAllPosts(pageable);
        List<PostResource> resources = postsPage.getContent()
                .stream()
                .map(this::convertToResource)
                .collect(Collectors.toList());

        return new PageImpl<>(resources, pageable, resources.size());
    }

    @Operation(summary = "Create Post", description = "Create a new Post", tags = {"posts"})
    @PostMapping("/posts")
    public PostResource createPost(@Valid @RequestBody SavePostResource resource) {
        Post post = convertToEntity(resource);
        return convertToResource(postService.createPost(post));
    }

    @Operation(summary = "Update Post", description = "Update Post for given Id", tags = {"posts"})
    @PutMapping("/posts/{postId}")
    public PostResource updatePost(@PathVariable Long postId, @RequestBody SavePostResource resource) {
        Post post = convertToEntity(resource);
        return convertToResource(postService.updatePost(postId, post));
    }

    @Operation(summary = "Delete Post", description = "Delete Posts with given Id", tags = {"posts"})
    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<?> deletePost(@PathVariable Long postId) {
        return postService.deletePost(postId);
    }


    private Post convertToEntity(SavePostResource resource) {
        return mapper.map(resource, Post.class);
    }

    private PostResource convertToResource(Post entity) {
        return mapper.map(entity, PostResource.class);
    }

}
