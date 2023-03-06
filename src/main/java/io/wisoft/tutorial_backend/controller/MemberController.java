package io.wisoft.tutorial_backend.controller;

import io.wisoft.tutorial_backend.service.MemberService;
import io.wisoft.tutorial_backend.service.dto.SigninRequest;
import io.wisoft.tutorial_backend.service.dto.SigninResponse;
import io.wisoft.tutorial_backend.service.dto.SignupDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth/")
public class MemberController {
    private final MemberService memberService;

    @PostMapping("signup")
    public ResponseEntity<SignupDto> signup(@RequestBody SignupDto request) {
        SignupDto response = memberService.createMember(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("signin")
    public ResponseEntity<SigninResponse> signin(@RequestBody SigninRequest request) {
        SigninResponse response = memberService.loginMember(request);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


}
