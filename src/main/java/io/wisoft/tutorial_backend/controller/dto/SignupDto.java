package io.wisoft.tutorial_backend.controller.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class SignupDto {
    @NotBlank
    private String email;
    @NotBlank
    private String password;
    @NotBlank
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
