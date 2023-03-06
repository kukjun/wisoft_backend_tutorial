package io.wisoft.tutorial_backend.service.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SigninResponse {
    private Long id;
    private String nickname;
    private String role;
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
