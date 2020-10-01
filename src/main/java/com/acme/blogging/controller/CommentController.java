package com.acme.blogging.controller;

import com.acme.blogging.domain.model.Comment;
import com.acme.blogging.domain.repository.CommentRepository;
import com.acme.blogging.domain.repository.PostRepository;
import com.acme.blogging.domain.service.CommentService;
import com.acme.blogging.exception.ResourceNotFoundException;
import com.acme.blogging.resource.CommentResource;
import com.acme.blogging.resource.SaveCommentResource;
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

@Tag(name = "posts")
@RestController
@RequestMapping("/api")
public class CommentController {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private CommentService commentService;


    @GetMapping("/posts/{postId}/comments")
    public Page<CommentResource> getAllCommentsByPostId(
            @PathVariable Long postId, Pageable pageable) {

        Page<Comment> commentPage = commentService.getAllCommentsByPostId(postId, pageable);
        List<CommentResource> resources = commentPage.getContent().stream().map(
                this::convertToResource).collect(Collectors.toList());
        return new PageImpl<>(resources, pageable, resources.size());
    }

    @GetMapping("/posts/{postId}/comments/{commentId}")
    public CommentResource getCommentByIdAndPostId(@PathVariable(name = "postId") Long postId,
                                                   @PathVariable(name = "commentId") Long commentId) {
        return convertToResource(commentService.getCommentByIdAndPostId(postId, commentId));
    }

    @PostMapping("/posts/{postId}/comments")
    public CommentResource createComment(
            @PathVariable Long postId,
            @Valid @RequestBody SaveCommentResource resource) {
        return convertToResource(commentService.createComment(postId, convertToEntity(resource)));
    }

    @PutMapping("/posts/{postId}/comments/{commentId}")
    public CommentResource updateComment(
            @PathVariable(value = "postId") Long postId,
            @PathVariable(value = "commentId") Long commentId,
            @Valid @RequestBody SaveCommentResource resource) {
        return convertToResource(commentService.updateComment(postId, commentId, convertToEntity(resource)));
    }

    @DeleteMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<?> deleteComment(
            @PathVariable(value = "postId") Long postId,
            @PathVariable(value = "commentId") Long commentId) {
        return commentService.deleteComment(postId, commentId);
    }

    private Comment convertToEntity(SaveCommentResource resource) {
        return mapper.map(resource, Comment.class);
    }

    private CommentResource convertToResource(Comment entity) {
        return mapper.map(entity, CommentResource.class);
    }

}
