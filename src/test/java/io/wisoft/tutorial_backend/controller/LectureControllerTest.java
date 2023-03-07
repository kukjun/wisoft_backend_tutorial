package io.wisoft.tutorial_backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.wisoft.tutorial_backend.domain.Lecture;
import io.wisoft.tutorial_backend.domain.Member;
import io.wisoft.tutorial_backend.domain.MemberRole;
import io.wisoft.tutorial_backend.repository.LectureRepository;
import io.wisoft.tutorial_backend.repository.MemberRepository;
import io.wisoft.tutorial_backend.service.dto.LectureDto;
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

import static org.junit.jupiter.api.Assertions.*;
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
        String nickname = "이미 저장된 데이터";
        Member member = Member.newInstance(
                email,
                password,
                nickname,
                MemberRole.ADMIN
        );
        memberRepository.save(member);

        LectureDto dto = LectureDto.newInstance(
                "테스트 Lecture 추가",
                "금,19시,21시",
                "테스트를 위한 내영",
                "김민기"
        );
        Lecture lecture = Lecture.newInstance(
                dto.getName(),
                dto.getSchedule(),
                dto.getContent(),
                dto.getTeacher(),
                member
        );
        lectureRepository.save(
                lecture
        );

        createdLectureId = lecture.getId();

    }

    @Test
    @DisplayName("강의 등록 통합 테스트 - 성공")
    public void lectureRegisterSuccessTest() throws Exception {
        //given
        LectureDto dto = LectureDto.newInstance(
                "스프링 간단 튜토리얼",
                "화,19시,21시",
                "스프림으로 만들어보는 간단한 웹 서버",
                "이국준"
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
    @DisplayName("강의 수정 통합 테스트 - 성공")
    public void lectureUpdateSuccessTest() throws Exception {
        //given
        LectureDto dto = LectureDto.newInstance(
                "국준 튜토리얼",
                "화,19시,21시",
                "스프림으로 만들어보는 간단한 웹 서버",
                "서동권"
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


}