package com.acme.blogging.domain.repository;

import com.acme.blogging.domain.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    public Optional<Post> findByTitle(String title);
}
