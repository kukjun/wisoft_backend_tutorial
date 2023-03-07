package io.wisoft.tutorial_backend.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "belongLecture")
    private List<Post> posts = new ArrayList<>();

    /**
     * 정적 생성자
     */
    public static Lecture newInstance(
            String name,
            String schedule,
            String content,
            String teacher,
            Member createMember
    ) {
        Lecture lecture = new Lecture();
        lecture.name = name;
        lecture.schedule = schedule;
        lecture.content = content;
        lecture.teacher = teacher;
        lecture.createMember = createMember;
        return lecture;
    }

    /**
     * 비지니스 로직
     */

    public void update(
            String name,
            String schedule,
            String content,
            String teacher
    ) {
        this.name = name;
        this.schedule = schedule;
        this.content = content;
        this.teacher = teacher;
    }
}
