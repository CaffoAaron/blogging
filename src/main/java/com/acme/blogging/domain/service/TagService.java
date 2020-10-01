package com.acme.blogging.domain.service;

import com.acme.blogging.domain.model.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface TagService {
    Page<Tag> getAllTags(Pageable pageable);

    Page<Tag> getAllTagsByPostId(Long postId, Pageable pageable);

    Tag getTagById(Long tagId);

    Tag createTag(Tag tag);

    Tag updateTag(Long tagId, Tag tagDetails);

    ResponseEntity<?> deleteTag(Long tagId);
}
