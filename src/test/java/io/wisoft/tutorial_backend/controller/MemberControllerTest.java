package io.wisoft.tutorial_backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.wisoft.tutorial_backend.domain.Member;
import io.wisoft.tutorial_backend.domain.MemberRole;
import io.wisoft.tutorial_backend.repository.MemberRepository;
import io.wisoft.tutorial_backend.controller.dto.SigninRequest;
import io.wisoft.tutorial_backend.controller.dto.SignupDto;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
class MemberControllerTest {

    private MockMvc mvc;

    @Autowired
    private WebApplicationContext ctx;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private JwtProvider jwtProvider;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private Long storedMemberId;

    @BeforeEach
    public void prepareTest() {
        mvc = MockMvcBuilders.webAppContextSetup(ctx)
                .alwaysDo(print())
                .addFilter(new CharacterEncodingFilter("UTF-8", true))
                .build();

        String email = "test@naver.com";
        String password = "$2a$12$tRT.z.BNFEdZ0jM6tBJ0a.h7XmS.trMJf/STNUF.0Ufcajwf/ovDG";
        String nickname = "이미 저장된 데이터";
        Member member = Member.newInstance(
                email,
                password,
                nickname,
                MemberRole.USER
        );
        memberRepository.save(member);
        storedMemberId = member.getId();

    }

    @Test
    @DisplayName("회원 생성 통합 테스트 - 성공")
    public void memberSignupSuccessTest() throws Exception {
        //given
        SignupDto requestDto = SignupDto.newInstance(
                "test123@naver.com",
                "testPassword",
                "테스트용데이터"
        );

        String jsonRequest = objectMapper.writeValueAsString(requestDto);


        //when
        //then
        String response = mvc.perform(
                post("/api/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest)
        ).andExpect(
                status().isCreated()
        ).andReturn().getResponse().getContentAsString();

        System.out.println(response);

    }

    @Test
    @DisplayName("회원 로그인 통합 테스트 - 성공")
    public void memberSigninSuccessTest() throws Exception {
        //given
        String email = "test@naver.com";
        String password = "password";
        SigninRequest request = SigninRequest.newInstance(
                email,
                password
        );
        String requestJson = objectMapper.writeValueAsString(request);

        //when
        //then
        String response = mvc.perform(
                post("/api/auth/signin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson)
        ).andExpect(
                status().isOk()
        ).andReturn().getResponse().getContentAsString();

        System.out.println("response = " + response);
    }

    @Test
    @DisplayName("회원 조회 통합 테스트 - 성공")
    public void findMemberSuccessTest() throws Exception {
        //given
        Member createMember = memberRepository.findById(storedMemberId).get();
        System.out.println("createMember: " + createMember.getNickname());
        String token = jwtProvider.generateToken(
                createMember.getId(),
                createMember.getNickname(),
                createMember.getRole().toString()
        );
        //when
        //then
        mvc.perform(
                get("/api/members/" + storedMemberId)
                        .header("Authorization", "Bearer " + token)
        ).andExpect(
                status().isOk()
        );
    }



}