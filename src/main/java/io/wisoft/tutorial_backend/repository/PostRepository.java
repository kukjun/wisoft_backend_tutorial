package io.wisoft.tutorial_backend.repository;

import io.wisoft.tutorial_backend.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByBelongLecture_Id(@Param("belongLectureId") Long lectureId);
}
