package io.wisoft.tutorial_backend.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Lecture extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @Column(unique = true)
    private String name;
    private String schedule;
    private String content;
    private String teacher;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member createMember;

    /**
     * 정적 생성자
     */
    public static Lecture newInstance(
            String name,
            String schedule,
            String content,
            String teacher
    ) {
        Lecture lecture = new Lecture();
        lecture.name = name;
        lecture.schedule = schedule;
        lecture.content = content;
        lecture.teacher = teacher;
        return lecture;
    }

}
