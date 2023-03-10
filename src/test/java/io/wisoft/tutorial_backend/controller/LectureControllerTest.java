package io.wisoft.tutorial_backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.wisoft.tutorial_backend.domain.Lecture;
import io.wisoft.tutorial_backend.domain.Member;
import io.wisoft.tutorial_backend.domain.MemberRole;
import io.wisoft.tutorial_backend.repository.LectureRepository;
import io.wisoft.tutorial_backend.repository.MemberRepository;
import io.wisoft.tutorial_backend.controller.dto.LectureDto;
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

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
class LectureControllerTest {
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

    public Long createdLectureId;

    @BeforeEach
    public void prepareTest() {
        mvc = MockMvcBuilders.webAppContextSetup(ctx)
                .alwaysDo(print())
                .addFilter(new CharacterEncodingFilter("UTF-8", true))
                .build();

        String email = "test@naver.com";
        // 'password' encoding
        String password = "$2a$12$tRT.z.BNFEdZ0jM6tBJ0a.h7XmS.trMJf/STNUF.0Ufcajwf/ovDG";
        String nickname = "?????? ????????? ?????????";
        Member member = Member.newInstance(
                email,
                password,
                nickname,
                MemberRole.ADMIN
        );
        memberRepository.save(member);

        String lectureName = "????????? Lecture ??????";
        String lectureSchedule = "???,19???,21???";
        String lectureContent = "???????????? ?????? ??????";
        String lectureTeacher = "?????????";
        LectureDto dto = LectureDto.newInstance(
                lectureName,
                lectureSchedule,
                lectureContent,
                lectureTeacher
        );
        Lecture lecture = Lecture.newInstance(
                dto.getName(),
                dto.getSchedule(),
                dto.getContent(),
                dto.getTeacher(),
                member
        );
        lectureRepository.save(lecture);

        createdLectureId = lecture.getId();

    }

    @Test
    @DisplayName("?????? ?????? ?????? ????????? - ??????")
    public void lectureRegisterSuccessTest() throws Exception {
        //given
        LectureDto dto = LectureDto.newInstance(
                "????????? ?????? ????????????",
                "???,19???,21???",
                "??????????????? ??????????????? ????????? ??? ??????",
                "?????????"
        );

        Member createMember = memberRepository.findByEmail("test@naver.com").get();
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
                post("/api/lectures")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest)
                        .header("Authorization", "Bearer " + token)
        ).andExpect(
                status().isCreated()
        ).andExpect(
                header().exists("Authorization")
        );

    }

    @Test
    @DisplayName("?????? ?????? ?????? ????????? - ??????")
    public void lectureUpdateSuccessTest() throws Exception {
        //given
        LectureDto dto = LectureDto.newInstance(
                "?????? ????????????",
                "???,19???,21???",
                "??????????????? ??????????????? ????????? ??? ??????",
                "?????????"
        );

        Member createMember = memberRepository.findByEmail("test@naver.com").get();
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
                patch("/api/lectures/" + createdLectureId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest)
                        .header("Authorization", "Bearer " + token)
        ).andExpect(
                status().isCreated()
        ).andExpect(header().exists("Authorization"));

    }

    @Test
    @DisplayName("?????? ?????? ?????? ?????? ????????? - ??????")
    public void findAllLectures() throws Exception {
        //given
        int expectedSize = 1;
        String expectLectureName = "????????? Lecture ??????";
        String expectLectureSchedule = "???,19???,21???";
        String expectLectureContent = "???????????? ?????? ??????";
        String expectLectureTeacher = "?????????";

        //when
        //then
        String StringResponse = mvc.perform(
                get("/api/lectures")
        ).andExpect(
                status().isOk()
        ).andReturn().getResponse().getContentAsString();

        System.out.println("StringResponse = " + StringResponse);
    }



}