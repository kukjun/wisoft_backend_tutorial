package io.wisoft.tutorial_backend.repository;

import io.wisoft.tutorial_backend.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
}
