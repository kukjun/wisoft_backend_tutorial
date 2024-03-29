package io.wisoft.tutorial_backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.wisoft.tutorial_backend.domain.*;
import io.wisoft.tutorial_backend.repository.CommentRepository;
import io.wisoft.tutorial_backend.repository.LectureRepository;
import io.wisoft.tutorial_backend.repository.MemberRepository;
import io.wisoft.tutorial_backend.repository.PostRepository;
import io.wisoft.tutorial_backend.controller.dto.CreateCommentDto;
import io.wisoft.tutorial_backend.controller.dto.UpdateCommentDto;
import io.wisoft.tutorial_backend.util.jwt.JwtProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
class CommentControllerTest {
    private MockMvc mvc;
    @Autowired
    private WebApplicationContext ctx;
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private LectureRepository lectureRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private CommentRepository commentRepository;

    private Long storedPostId;
    private Long storedComment;
    private Long currentMemberId;

    @BeforeEach
    public void prepareTest() {
        mvc = MockMvcBuilders.webAppContextSetup(ctx)
                .alwaysDo(print())
                .addFilter(new CharacterEncodingFilter("UTF-8", true))
                .build();

        String lectureCreateMemberEmail = "test@naver.com";
        // 'password' encoding
        String lectureCreateMemberPassword = "$2a$12$tRT.z.BNFEdZ0jM6tBJ0a.h7XmS.trMJf/STNUF.0Ufcajwf/ovDG";
        String lectureCreateMemberNickname = "이미 저장된 데이터";
        Member lectureCreateMember = Member.newInstance(
                lectureCreateMemberEmail,
                lectureCreateMemberPassword,
                lectureCreateMemberNickname,
                MemberRole.ADMIN
        );
        memberRepository.save(lectureCreateMember);

        Member postCreateMember = Member.newInstance(
                "postCreate@naver.com",
                "$2a$12$tRT.z.BNFEdZ0jM6tBJ0a.h7XmS.trMJf/STNUF.0Ufcajwf/ovDG",
                "post 생성하는 멤버",
                MemberRole.USER
        );
        memberRepository.save(postCreateMember);

        Member commentCreateMember = Member.newInstance(
                "commentCreate@naver.com",
                "$2a$12$tRT.z.BNFEdZ0jM6tBJ0a.h7XmS.trMJf/STNUF.0Ufcajwf/ovDG",
                "comment 생성하는 멤버",
                MemberRole.USER
        );
        memberRepository.save(commentCreateMember);

        currentMemberId = commentCreateMember.getId();

        String lectureName = "테스트 Lecture 추가";
        String lectureSchedule = "금,19시,21시";
        String lectureContent = "테스트를 위한 내용";
        String lectureTeacher = "김민기";
        Lecture lecture = Lecture.newInstance(
                lectureName,
                lectureSchedule,
                lectureContent,
                lectureTeacher,
                lectureCreateMember
        );
        lectureRepository.save(lecture);

        Post post = Post.newInstance(
                "테스트 Post 추가",
                "테스트를 위한 Post 추가",
                postCreateMember,
                lecture

        );
        postRepository.save(post);
        storedPostId = post.getId();

        Comment comment = Comment.newInstance(
                "storedComment",
                commentCreateMember,
                post
        );
        commentRepository.save(comment);
        storedComment = comment.getId();
    }

    @Test
    @DisplayName("덧글 등록 통합 테스트 - 성공")
    public void commentRegisterSuccessTest() throws Exception {
        //given
        CreateCommentDto dto = CreateCommentDto.newInstance(
                "덧글 작성 등록",
                storedPostId
        );
        String jsonRequest = objectMapper.writeValueAsString(dto);

        Member createMember = memberRepository.findById(currentMemberId).get();
        System.out.println("createMember: " + createMember.getNickname());
        String token = jwtProvider.generateToken(
                createMember.getId(),
                createMember.getNickname(),
                createMember.getRole().toString()
        );

        //when
        //then
        mvc.perform(
                post("/api/comments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest)
                        .header("Authorization", "Bearer " + token)
        ).andExpect(
                status().isCreated()
        );
    }

    @Test
    @DisplayName("덧글 수정 통합 테스트 - 성공")
    public void updateCommentSuccessTest() throws Exception {
        //given
        UpdateCommentDto dto = UpdateCommentDto.newInstance(
                "저장된 테스트"
        );
        String jsonRequest = objectMapper.writeValueAsString(dto);

        Member createMember = memberRepository.findById(currentMemberId).get();
        System.out.println("createMember: " + createMember.getNickname());
        String token = jwtProvider.generateToken(
                createMember.getId(),
                createMember.getNickname(),
                createMember.getRole().toString()
        );
        //when
        //then
        mvc.perform(
                patch("/api/comments/" + storedComment)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest)
                        .header("Authorization", "Bearer " + token)
        ).andExpect(
                status().isCreated()
        );
    }


}