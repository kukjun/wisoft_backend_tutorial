package io.wisoft.tutorial_backend.service.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SigninRequest {
    private String email;
    private String password;

    public static SigninRequest newInstance(
            String email,
            String password
    ) {
        SigninRequest request = new SigninRequest();
        request.email = email;
        request.password = password;
        return request;
    }

}
