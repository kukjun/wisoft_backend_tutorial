package io.wisoft.tutorial_backend.repository;

import io.wisoft.tutorial_backend.domain.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LectureRepository extends JpaRepository<Lecture, Long> {
}
