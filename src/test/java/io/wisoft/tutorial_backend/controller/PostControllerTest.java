package io.wisoft.tutorial_backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.wisoft.tutorial_backend.domain.*;
import io.wisoft.tutorial_backend.repository.CommentRepository;
import io.wisoft.tutorial_backend.repository.LectureRepository;
import io.wisoft.tutorial_backend.repository.MemberRepository;
import io.wisoft.tutorial_backend.repository.PostRepository;
import io.wisoft.tutorial_backend.controller.dto.PostDto;
import io.wisoft.tutorial_backend.controller.dto.UpdatePostDto;
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
class PostControllerTest {
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
    private Long storedLectureId;

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
        String lectureCreateMemberNickname = "?????? ????????? ?????????";
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
                "post ???????????? ??????",
                MemberRole.USER
        );
        memberRepository.save(postCreateMember);

        currentMemberId = postCreateMember.getId();

        Member commentCreateMember = Member.newInstance(
                "commentCreate@naver.com",
                "$2a$12$tRT.z.BNFEdZ0jM6tBJ0a.h7XmS.trMJf/STNUF.0Ufcajwf/ovDG",
                "comment ???????????? ??????",
                MemberRole.USER
        );
        memberRepository.save(commentCreateMember);

        String lectureName = "????????? Lecture ??????";
        String lectureSchedule = "???,19???,21???";
        String lectureContent = "???????????? ?????? ??????";
        String lectureTeacher = "?????????";
        Lecture lecture = Lecture.newInstance(
                lectureName,
                lectureSchedule,
                lectureContent,
                lectureTeacher,
                lectureCreateMember
        );
        lectureRepository.save(lecture);
        storedLectureId = lecture.getId();

        Post post = Post.newInstance(
                "????????? Post ??????",
                "???????????? ?????? Post ??????",
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

    }

    @Test
    @DisplayName("????????? ?????? ?????? ????????? - ??????")
    public void postRegisterSuccessTest() throws Exception {
        //given
        PostDto dto = PostDto.newInstance(
                "????????? ???????????? ????????????",
                "??? ????????????",
                storedLectureId
        );


        Member createMember = memberRepository.findById(currentMemberId).get();
        System.out.println("createMember: " + createMember.getNickname());
        String token = jwtProvider.generateToken(
                createMember.getId(),
                createMember.getNickname(),
                createMember.getRole().toString()
        );

        String jsonRequest = objectMapper.writeValueAsString(dto);

        //when
        //then
        mvc.perform(
                post("/api/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest)
                        .header("Authorization", "Bearer " + token)
        ).andExpect(
                status().isCreated()
        );
    }

    @Test
    @DisplayName("?????? ?????? ?????? ????????? ?????? ?????? ????????? - ?????? ")
    public void findAllPostFromLectureId() throws Exception {
        //given
        Member createMember = memberRepository.findById(currentMemberId).get();
        System.out.println("createMember: " + createMember.getNickname());

        //when
        //then
        mvc.perform(
                get("/api/posts")
                        .param("lectureId", String.valueOf(storedLectureId))
        ).andExpect(
                status().isOk()
        );
    }

    @Test
    @DisplayName("????????? ?????? ?????? ????????? - ??????")
    public void updatePost() throws Exception {
        //given
        UpdatePostDto dto = UpdatePostDto.newInstance(
                "????????? ???????????? ???????????? ????????????.",
                "??????????????????"
        );

        Member createMember = memberRepository.findById(currentMemberId).get();
        System.out.println("createMember: " + createMember.getNickname());
        String token = jwtProvider.generateToken(
                createMember.getId(),
                createMember.getNickname(),
                createMember.getRole().toString()
        );

        String jsonRequest = objectMapper.writeValueAsString(dto);

        //when
        //then
        mvc.perform(
                patch("/api/posts/" + storedPostId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest)
                        .header("Authorization", "Bearer " + token)

        ).andExpect(
                status().isCreated()
        );
    }

    @Test
    @DisplayName("????????? ?????? ?????? ?????? ????????? - ??????")
    public void findDetailPost() throws Exception {
        //given
        Member createMember = memberRepository.findById(currentMemberId).get();
        System.out.println("createMember: " + createMember.getNickname());

        //when
        //then
        mvc.perform(
                get("/api/posts/" + storedPostId)
        ).andExpect(
                status().isOk()
        );
    }


}