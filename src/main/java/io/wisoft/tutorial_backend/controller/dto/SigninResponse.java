package io.wisoft.tutorial_backend.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SigninResponse {

    @NotNull
    private Long id;
    @NotBlank
    private String nickname;
    @NotBlank
    private String role;
    @NotBlank
    private String accessToken;

    public static SigninResponse newInstance(
            Long id,
            String nickname,
            String role,
            String accessToken
    ) {
        SigninResponse response = new SigninResponse();
        response.id = id;
        response.nickname = nickname;
        response.role = role;
        response.accessToken = accessToken;
        return response;
    }


}
