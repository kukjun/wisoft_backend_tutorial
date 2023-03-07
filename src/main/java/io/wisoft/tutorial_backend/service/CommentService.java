package io.wisoft.tutorial_backend.service;

import io.wisoft.tutorial_backend.domain.Comment;
import io.wisoft.tutorial_backend.domain.Member;
import io.wisoft.tutorial_backend.domain.Post;
import io.wisoft.tutorial_backend.repository.CommentRepository;
import io.wisoft.tutorial_backend.repository.MemberRepository;
import io.wisoft.tutorial_backend.repository.PostRepository;
import io.wisoft.tutorial_backend.service.dto.CommentDto;
import io.wisoft.tutorial_backend.service.dto.CreateCommentDto;
import io.wisoft.tutorial_backend.service.dto.UpdateCommentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;

    @Transactional
    public void registerComment(CreateCommentDto dto, Long currentMemberId) {
        Member member = memberRepository.findById(currentMemberId).orElseThrow(
                () -> new RuntimeException("member not found")
        );
        Post post = postRepository.findById(dto.getPostId()).orElseThrow(
                () -> new RuntimeException("post not found")
        );
        Comment comment = Comment.newInstance(dto.getContent(), member, post);

        commentRepository.save(comment);
    }

    @Transactional
    public void updateComment(UpdateCommentDto dto, Long currentMemberId, Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new RuntimeException("comment not found")
        );
        if (comment.getCreateMember().getId() != currentMemberId) {
            throw new RuntimeException("current member mismatch");
        }
        comment.update(dto);
    }
}
