package io.wisoft.tutorial_backend.service;

import io.wisoft.tutorial_backend.domain.Member;
import io.wisoft.tutorial_backend.domain.MemberRole;
import io.wisoft.tutorial_backend.handler.exception.service.CurrentMemberMismatchException;
import io.wisoft.tutorial_backend.handler.exception.service.MemberNotFoundException;
import io.wisoft.tutorial_backend.handler.exception.service.PasswordMismatchException;
import io.wisoft.tutorial_backend.repository.MemberRepository;
import io.wisoft.tutorial_backend.controller.dto.MemberInformationDto;
import io.wisoft.tutorial_backend.controller.dto.SigninRequest;
import io.wisoft.tutorial_backend.controller.dto.SigninResponse;
import io.wisoft.tutorial_backend.controller.dto.SignupDto;
import io.wisoft.tutorial_backend.util.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    @Transactional
    public SignupDto createMember(SignupDto signupDto) {
        Member member = Member.newInstance(
                signupDto.getEmail(),
                passwordEncoder.encode(signupDto.getPassword()),
                signupDto.getNickname(),
                MemberRole.USER
        );
        memberRepository.save(member);
        return SignupDto.newInstance(
                member.getEmail(),
                member.getPassword(),
                member.getNickname()
        );
    }

    public SigninResponse loginMember(SigninRequest request) {
        Member member = memberRepository.findByEmail(request.getEmail())
                .orElseThrow(
                        ()-> new MemberNotFoundException("email not fount")
                );
        if (!passwordEncoder.matches(request.getPassword(), member.getPassword())) {
            throw new PasswordMismatchException("password mismatch");
        } else {
            String token = jwtProvider.generateToken(
                    member.getId(), member.getNickname(), member.getRole().toString()
            );
            return SigninResponse.newInstance(
                    member.getId(),
                    member.getNickname(),
                    member.getRole().toString(),
                    token
            );
        }
    }

    public MemberInformationDto findMember(Long memberId, Long currentMemberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(
                        () -> new MemberNotFoundException("member not found")
                );

        if (member.getId() != currentMemberId) {
            throw new CurrentMemberMismatchException("current member mismatch");
        }
        return MemberInformationDto.newInstance(
                member.getId(),
                member.getNickname(),
                member.getRole().toString()
        );
    }


}
