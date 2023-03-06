package io.wisoft.tutorial_backend.service.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberInformationDto {
    private Long id;
    private String nickname;
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
