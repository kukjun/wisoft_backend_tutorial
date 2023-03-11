package io.wisoft.tutorial_backend.controller;

import io.wisoft.tutorial_backend.service.MemberService;
import io.wisoft.tutorial_backend.controller.dto.MemberInformationDto;
import io.wisoft.tutorial_backend.controller.dto.SigninRequest;
import io.wisoft.tutorial_backend.controller.dto.SigninResponse;
import io.wisoft.tutorial_backend.controller.dto.SignupDto;
import io.wisoft.tutorial_backend.util.jwt.JwtCommunicationServlet;
import io.wisoft.tutorial_backend.util.jwt.JwtProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MemberController {
    private final MemberService memberService;
    private final JwtCommunicationServlet jwtCommunicationServlet;
    private final JwtProvider jwtProvider;

    @PostMapping("/auth/signup")
    public ResponseEntity<SignupDto> signup(@RequestBody @Valid final SignupDto request) {
        SignupDto response = memberService.createMember(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/auth/signin")
    public ResponseEntity<SigninResponse> signin(@RequestBody @Valid final SigninRequest request) {
        SigninResponse response = memberService.loginMember(request);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/members/{memberId}")
    public ResponseEntity<MemberInformationDto> find(HttpServletRequest request, @PathVariable("memberId") Long memberId) {
        String token = jwtCommunicationServlet.extract(request);
        Long userId = Long.parseLong(jwtProvider.getClaims(token, "userId"));

        MemberInformationDto response = memberService.findMember(memberId, userId);

        return ResponseEntity.status(HttpStatus.OK).body(response);

    }

}
