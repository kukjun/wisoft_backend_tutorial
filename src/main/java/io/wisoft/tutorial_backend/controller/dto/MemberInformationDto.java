package io.wisoft.tutorial_backend.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberInformationDto {
    @NotNull
    private Long id;
    @NotBlank
    private String nickname;
    @NotBlank
    private String role;

    public static MemberInformationDto newInstance(
            Long id,
            String nickname,
            String role
    ) {
        MemberInformationDto dto = new MemberInformationDto();
        dto.id = id;
        dto.nickname = nickname;
        dto.role = role;
        return dto;
    }


}
