package io.wisoft.tutorial_backend.repository;

import io.wisoft.tutorial_backend.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}
