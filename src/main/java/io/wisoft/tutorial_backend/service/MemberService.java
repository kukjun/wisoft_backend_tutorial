package io.wisoft.tutorial_backend.service;

import io.wisoft.tutorial_backend.domain.Member;
import io.wisoft.tutorial_backend.domain.MemberRole;
import io.wisoft.tutorial_backend.repository.MemberRepository;
import io.wisoft.tutorial_backend.service.dto.SignupDto;
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

    @Transactional
    public SignupDto signup(SignupDto signupDto) {
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

}
