package io.wisoft.tutorial_backend.service;

import io.wisoft.tutorial_backend.domain.Comment;
import io.wisoft.tutorial_backend.domain.Lecture;
import io.wisoft.tutorial_backend.domain.Member;
import io.wisoft.tutorial_backend.domain.Post;
import io.wisoft.tutorial_backend.repository.LectureRepository;
import io.wisoft.tutorial_backend.repository.MemberRepository;
import io.wisoft.tutorial_backend.repository.PostRepository;
import io.wisoft.tutorial_backend.service.dto.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {
    private final MemberRepository memberRepository;
    private final LectureRepository lectureRepository;
    private final PostRepository postRepository;

    private final Logger logger = LoggerFactory.getLogger(PostDto.class);
    @Transactional
    public void createPost(PostDto dto, Long createMemberId) {
        Long lectureId = dto.getLectureId();
        logger.debug("lectureId: " + lectureId);
        Lecture belongLecture = lectureRepository.findById(lectureId).orElseThrow(
                () -> new RuntimeException("lecture not found")
        );
        Member createMember = memberRepository.findById(createMemberId).orElseThrow(
                () -> new RuntimeException("member not found")
        );
        Post post = Post.newInstance(
                dto.getTitle(),
                dto.getContent(),
                createMember,
                belongLecture
        );
        postRepository.save(post);

    }

    @Transactional
    public void updatePost(UpdatePostDto dto, Long postId, Long currentMemberId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new RuntimeException("post not found")
        );

        if (post.getCreateMember().getId() != currentMemberId) {
            throw new RuntimeException("createMember Mismatch");
        }

        post.update(dto);

    }

    public List<SimplePostDto> findAllPostFromLectureId(Long lectureId) {
        List<Post> postList = postRepository.findAllByBelongLecture_Id(lectureId);
        List<SimplePostDto> simplePostList = new ArrayList<>();

        for (Post post : postList) {
            simplePostList.add(
                    SimplePostDto.newInstance(post.getId(), post.getTitle())
            );
        }

        return simplePostList;
    }

    public DetailPostDto findPost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new RuntimeException("post not found")
        );
        List<Comment> commentList = post.getComments();
        List<CommentDto> comments = new ArrayList<>();
        for (Comment comment : commentList) {
            comments.add(CommentDto.newInstance(
                    comment.getId(),
                    comment.getContent(),
                    comment.getCreateMember().getId()
            ));
        }
        return DetailPostDto.newInstance(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getBelongLecture().getId(),
                post.getCreateMember().getId(),
                comments
        );
    }



}
