package io.wisoft.tutorial_backend.service.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class SignupDto {
    private String email;
    private String password;
    private String nickname;

    public static SignupDto newInstance(
            String email,
            String password,
            String nickname
    ) {
        SignupDto signupDto = new SignupDto();
        signupDto.email = email;
        signupDto.password = password;
        signupDto.nickname = nickname;
        return signupDto;
    }
}
