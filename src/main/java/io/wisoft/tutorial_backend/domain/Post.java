package io.wisoft.tutorial_backend.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

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

    @OneToMany(mappedBy = "belongPost")
    private List<Comment> comments;

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

    /**
     * 연관관계 편의 메서드
     */
    public void connectBelongLecture(Lecture lecture) {
        this.belongLecture = lecture;
        lecture.getPosts().add(this);
    }
    public void disconnectBelongLecture() {
        this.belongLecture.getPosts().remove(this);
    }

}
