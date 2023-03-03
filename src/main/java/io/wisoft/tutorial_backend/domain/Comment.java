package io.wisoft.tutorial_backend.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member createMember;

    @ManyToOne(fetch = FetchType.LAZY)
    private Post belongPost;

    /**
     * 정적 생성자
     */
    public static Comment newInstance(
            String content,
            Member createMember,
            Post belongPost
    ) {
        Comment comment = new Comment();
        comment.content = content;
        comment.createMember = createMember;
        comment.belongPost = belongPost;
        return comment;
    }
}
