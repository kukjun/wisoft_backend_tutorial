package io.wisoft.tutorial_backend.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    private String title;
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member createMember;

    @ManyToOne(fetch = FetchType.LAZY)
    private Lecture belongLecture;

    /**
     * 정적 생성자
     */
    public static Post newInstance(
            String title,
            String content,
            Member createMember,
            Lecture belongLecture
    ) {
        Post post = new Post();
        post.title = title;
        post.content = content;
        post.createMember = createMember;
        post.belongLecture = belongLecture;
        return post;
    }

}
